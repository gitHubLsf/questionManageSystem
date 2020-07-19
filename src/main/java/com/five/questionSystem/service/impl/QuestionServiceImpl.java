package com.five.questionSystem.service.impl;

import com.five.questionSystem.common.DBUtils;
import com.five.questionSystem.common.MD5Hash;
import com.five.questionSystem.dao.PracticeDao;
import com.five.questionSystem.entity.ExamPart;
import com.five.questionSystem.entity.Question;
import com.five.questionSystem.dao.QuestionDao;
import com.five.questionSystem.entity.PersonQuestion;
import com.five.questionSystem.service.PracticeService;
import com.five.questionSystem.service.QuestionService;
import com.five.questionSystem.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Override
    public Question querySysQuestionById(Integer id) {
        return questionDao.querySysQuestionById(id);
    }


    @Override
    public boolean insertQuestionToSys(Question question) {
        question.setId(db.getKey("QUESTION_ID"));

        //计算题目的哈希值
        String questionName = question.getName();
        String hashName = MD5Hash.md5(questionName);

        //查看数据库中是否有这道题
        Question hasQuestion = this.questionDao.hasQuestionInSystem(hashName);
        if (Objects.isNull(hasQuestion)) {
            question.setHashCode(hashName);
            this.questionDao.insertQuestionToSystem(question);
            return true;
        }
        return false;
    }


    @Override
    public void updateSysQuestion(QuesUpdateReq req) {
        req.valiation();
        Question question = new Question();
        BeanUtils.copyProperties(req, question);
        questionDao.updateSysQuestion(question);
    }


    @Override
    public void deleteSysQuestionById(Integer id) {
        questionDao.deleteSysQuestionById(id);
    }


    @Override
    public RespInfo querySystemQuestion(QuesReq req) {
        req.volidation();

        Question question = new Question();
        BeanUtils.copyProperties(req, question);


        PageHelper.startPage(req.getPage(), req.getLimit());
        List<Question> list = questionDao.querySystemQuestion(question);

        PageInfo<Question> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public void batchDeleteSysQuestion(Integer[] ids) {
        questionDao.batchDeleteSysQuestion(ids);
    }


    @Override
    public RespInfo queryPersonQuestion(QuesReq req, Integer currentId) {
        req.volidation();

        PersonQuestion question = new PersonQuestion();
        BeanUtils.copyProperties(req, question);
        question.setUserId(currentId);

        PageHelper.startPage(req.getPage(), req.getLimit());
        List<Question> list = questionDao.queryPersonQuestion(question);

        PageInfo<Question> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public void deletePersonQuestion(Integer id) {
        questionDao.deletePersonQuestion(id);
    }


    @Override
    public void batchDeletePersonQuestion(Integer[] ids) {
        questionDao.batchDeletePersonQuestion(ids);
    }


    @Override
    public Question queryPersonQuestionById(Integer id) {
        return questionDao.queryPersonQuestionById(id);
    }


    @Override
    public void updatePersonQuestion(QuesUpdateReq req) {
        req.valiation();
        PersonQuestion question = new PersonQuestion();
        BeanUtils.copyProperties(req, question);
        this.questionDao.updatePersonQuestion(question);
    }


    @Override
    public boolean addQuestionByPerson(QuestionAddReq req, Integer id) {
        req.voliation();

        //判断题库中是否存在此题
        //计算题目的哈希值
        String hashName = MD5Hash.md5(req.getName());
        //查询题库中是否有此题
        Question hasQuestion = questionDao.hasQuestionInSystem(hashName);
        if (Objects.isNull(hasQuestion)) {
            //题库中不存在
            //将此题添加到题库
            Question question = new Question();
            BeanUtils.copyProperties(req, question);
            question.setId(db.getKey("QUESTION_ID"));
            question.setHashCode(MD5Hash.md5(question.getName()));
            this.questionDao.insertQuestionToSystem(question);

            //添加到个人错题列表
            PersonQuestion pq = new PersonQuestion();
            BeanUtils.copyProperties(req, pq);
            pq.setId(db.getKey("USER_QUESTION_ID"));
            pq.setUserId(id);
            pq.setQuestionId(db.getCurrKey("QUESTION_ID"));
            this.questionDao.addQuestionByPerson(pq);

            //没有在题库中重复添加，此处返回真
            return true;
        }

        //如果系统中有这道题，则直接添加到个人题库即可
        //查看个人题库是否有这套题
        Search search = new Search();
        search.setUserId(id);
        search.setQuestionId(hasQuestion.getId());

        PersonQuestion peq = this.questionDao.hasPersonQuestion(search);
        if (Objects.isNull(peq)) {
            //添加到个人题库中
            PersonQuestion pq = new PersonQuestion();
            BeanUtils.copyProperties(req, pq);
            pq.setId(db.getKey("USER_QUESTION_ID"));
            pq.setUserId(id);
            pq.setQuestionId(hasQuestion.getId());
            this.questionDao.addQuestionByPerson(pq);
        }

        return false;
    }


    @Override
    public RespInfo queryStuQuestionUnderTea(QuesReq req, Integer id) {
        req.volidation();

        PersonQuestion question = new PersonQuestion();
        BeanUtils.copyProperties(req, question);
        question.setUserId(id);

        PageHelper.startPage(req.getPage(), req.getLimit());
        List<Question> list = questionDao.queryStuQuestionUnderTea(question);

        PageInfo<Question> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public void chooseQuestionToPractice(Integer[] ids, Integer userId) {
        if (ids == null || ids.length == 0) {
            return;
        }

        // 选择题列表
        ArrayList<Question> chooseList = new ArrayList<>();
        // 填空题列表
        ArrayList<Question> kongList = new ArrayList<>();
        // 问答题列表
        ArrayList<Question> ansList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            //根据id到个人题库中查询这道错题
            Question question = this.questionDao.queryPersonQuestionById(ids[i]);
            //分类错题
            if (question.getType() == 0) {
                //选择题
                chooseList.add(question);
            } else if (question.getType() == 1) {
                //填空题列表
                kongList.add(question);
            } else if (question.getType() == 2) {
                ansList.add(question);
            }
        }

        //设置组成部分
        ArrayList<ExamPart> examParts = new ArrayList<>();

        if (chooseList.size() != 0) {
            ExamPart examPart = new ExamPart();
            examPart.setType(0);
            examPart.setQuestionCount(chooseList.size());
            examPart.setQuestions(chooseList);
            examParts.add(examPart);
        }

        if (kongList.size() != 0) {
            ExamPart examPart = new ExamPart();
            examPart.setType(1);
            examPart.setQuestionCount(kongList.size());
            examPart.setQuestions(kongList);
            examParts.add(examPart);
        }

        if (ansList.size() != 0) {
            ExamPart examPart = new ExamPart();
            examPart.setType(2);
            examPart.setQuestionCount(ansList.size());
            examPart.setQuestions(ansList);
            examParts.add(examPart);
        }


        //将组成部分添加到数据库
        //试卷id
        Integer curr = db.getCurrKey("PRACTICE_ID") + 1;

        //试卷组成部分数组
        if (examParts != null && examParts.size() > 0) {
            for (int i = 0; i < examParts.size(); i++) {
                //为这部分组成设置所属的试卷ID
                examParts.get(i).setExampaperId(curr);

                //将这部分组成添加到试卷中
                this.practiceService.addPart(examParts.get(i));

                //将组成部分所属错题添加到组卷错题列表中
                //获取组成部分id
                Integer pid = db.getCurrKey("PART_ID");
                List<Question> questions = examParts.get(i).getQuestions();
                for (Question q : questions) {
                    this.practiceDao.addPartQuestion(pid, q.getQuestionId());
                }
            }
        }

        //添加一份试卷
        practiceService.insert(userId);
    }


    @Override
    public void teacherImportQuesFromSys(Integer[] ids, Integer id) {
        if (ids == null || ids.length == 0) {
            return;
        }

        Search search = new Search();
        for (int i = 0; i < ids.length; i++) {
            //查看此题在个人题库中是否存在
            search.setUserId(id);
            search.setQuestionId(ids[i]);
            PersonQuestion pq = this.questionDao.hasPersonQuestion(search);
            if (pq == null) {
                //查询这道错题的信息
                Question question = this.questionDao.querySysQuestionById(ids[i]);
                //导入到个人题库
                PersonQuestion pn = new PersonQuestion();
                pn.setUserId(id);
                pn.setQuestionId(ids[i]);
                pn.setDifficulty(question.getDifficulty());
                pn.setGrade(question.getGrade());
                this.questionDao.addQuestionByPerson(pn);
            }
        }
    }


    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private DBUtils db;

    @Autowired
    private RespInfo respInfo;

    @Autowired
    private PracticeService practiceService;

    @Autowired
    private PracticeDao practiceDao;
}