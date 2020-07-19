package com.five.questionSystem.service.impl;

import com.five.questionSystem.common.DownloadUtils;
import com.five.questionSystem.common.DBUtils;
import com.five.questionSystem.entity.ExamPaper;
import com.five.questionSystem.dao.PracticeDao;
import com.five.questionSystem.entity.ExamPart;
import com.five.questionSystem.service.PracticeService;
import com.five.questionSystem.vo.PracticeReq;
import com.five.questionSystem.vo.ExamPartReq;
import com.five.questionSystem.vo.RespInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class PracticeServiceImpl implements PracticeService {

    @Autowired
    private PracticeDao practiceDao;

    @Autowired
    private DBUtils db;

    @Autowired
    private RespInfo respInfo;


    @Override
    public ExamPaper queryById(Integer id) {
        return practiceDao.queryById(id);
    }


    @Override
    public void deleteById(int id) {
        practiceDao.deleteById(id);
    }


    @Override
    public void batchDelete(Integer[] ids) {
        practiceDao.batchDelete(ids);
    }


    @Override
    public RespInfo querySysPractice(PracticeReq req) {
        req.valiation();

        ExamPaper examPaper = new ExamPaper();
        BeanUtils.copyProperties(req, examPaper);

        PageHelper.startPage(req.getPage(), req.getLimit());
        List<ExamPaper> list = this.practiceDao.querySysPractice(examPaper);
        PageInfo<ExamPaper> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public boolean add(ExamPartReq[] parts) {
        if (parts == null || parts.length == 0) {
            return false;
        }

        ArrayList<ExamPart> examParts = new ArrayList<>();
        boolean flag = false;

        for (int i = 0; i < parts.length; i++) {
            ExamPartReq part = parts[i];
            part.valiation();

            String type = part.getType();
            String difficulty = part.getDifficulty();
            String grade = part.getGrade();
            Integer question_count = part.getQuestion_count();

            if ((type == null || "".equals(type))
                    && (difficulty == null || "".equals(difficulty))
                    && (grade == null || "".equals(grade))
                    && (question_count == null || question_count <= 0)) {
                continue;
            }

            int types = 0;
            if (type != null) {
                if ("选择题".equals(type)) {
                    types = 0;
                } else if ("填空题".equals(type)) {
                    types = 1;
                } else if ("问答题".equals(type)) {
                    types = 2;
                }
            }

            int diffi = 0;
            if (difficulty != null) {
                if ("简单".equals(difficulty)) {
                    diffi = 0;
                } else if ("较难".equals(difficulty)) {
                    diffi = 1;
                } else if ("很难".equals(difficulty)) {
                    diffi = 2;
                }
            }

            int grades = 0;
            if (grade != null) {
                if ("一年级".equals(grade)) {
                    grades = 0;
                } else if ("二年级".equals(grade)) {
                    grades = 1;
                } else if ("三年级".equals(grade)) {
                    grades = 2;
                } else if ("四年级".equals(grade)) {
                    grades = 3;
                } else if ("五年级".equals(grade)) {
                    grades = 4;
                } else if ("六年级".equals(grade)) {
                    grades = 5;
                } else if ("初一".equals(grade)) {
                    grades = 6;
                } else if ("初二".equals(grade)) {
                    grades = 7;
                } else if ("初三".equals(grade)) {
                    grades = 8;
                }
            }

            ExamPart examPart = new ExamPart();
            examPart.setType(types);
            examPart.setDifficulty(diffi);
            examPart.setGrade(grades);
            examPart.setQuestionCount(question_count);
            examParts.add(examPart);
            flag = true;
        }

        if (!flag) {
            return false;
        }

        //添加一份试卷
        //this.insert();

        //获取试卷的组成部分，添加到数据库，并根据组成部分的策略设置，随机抽取题目
        //试卷id
        Integer curr = db.getCurrKey("EXAMPAPER_ID");

        //试卷组成部分数组
        if (examParts != null && examParts.size() > 0) {
            for (int i = 0; i < parts.length; i++) {
                //为这部分组成设置所属的试卷ID
                examParts.get(i).setExampaperId(curr);

                //将这部分组成添加到试卷中
                this.addPart(examParts.get(i));

                //根据组成部分的特性，去错题列表挑选错题
                List<Integer> ids = this.practiceDao.getPartQuestion(examParts.get(i));

                //ids是所有符合条件的题目，我们从里面筛选出部分题,进行随机组卷
                int truSize = ids.size();
                int needSize = examParts.get(i).getQuestionCount();
                List<Integer> trueId = new ArrayList<>();
                if (needSize < truSize) {
                    for (int j = 0; j < needSize; j++) {
                        Random random = new Random();
                        while (true) {
                            int i1 = ids.get(random.nextInt(truSize));
                            if (!trueId.contains(i1)) {
                                trueId.add(i1);
                                break;
                            }
                        }
                    }
                } else {
                    trueId = ids;
                }

                //将上述错题添加到组卷错题列表中
                //获取组成部分id
                Integer pid = db.getCurrKey("EXAMPART_ID");
                for (Integer id : trueId) {
                    this.practiceDao.addPartQuestion(pid, id);
                }
            }
        }

        return false;
    }


    @Override
    public void insert(int id) {
        ExamPaper examPaper = new ExamPaper();
        examPaper.setId(db.getKey("PRACTICE_ID"));
        examPaper.setName(DownloadUtils.getRandomFileName_());
        examPaper.setUserId(id);
        practiceDao.insert(examPaper);
    }


    @Override
    public void addPart(ExamPart part) {
        part.setId(db.getKey("PART_ID"));
        practiceDao.addPart(part);
    }


    @Override
    public ExamPaper getById(Integer id) {
        return practiceDao.getById(id);
    }


    @Override
    public ExamPaper queryPracticeById(Integer id) {
        return practiceDao.queryPracticeById(id);
    }


    @Override
    public void update(PracticeReq req) {
        req.valiation();
        ExamPaper examPaper = new ExamPaper();
        BeanUtils.copyProperties(req, examPaper);
        this.practiceDao.update(examPaper);
    }


    @Override
    public RespInfo queryPersonPractice(PracticeReq req, int id) {
        req.valiation();

        ExamPaper examPaper = new ExamPaper();
        BeanUtils.copyProperties(req, examPaper);
        examPaper.setUserId(id);

        PageHelper.startPage(req.getPage(), req.getLimit());
        List<ExamPaper> list = this.practiceDao.queryPersonPractice(examPaper);
        PageInfo<ExamPaper> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public boolean teacherAddPractice(ExamPartReq[] parts, Integer id) {
        if (parts == null || parts.length == 0) {
            return false;
        }

        ArrayList<ExamPart> examParts = new ArrayList<>();
        boolean flag = false;

        for (int i = 0; i < parts.length; i++) {
            ExamPartReq part = parts[i];
            part.valiation();

            String type = part.getType();
            String difficulty = part.getDifficulty();
            String grade = part.getGrade();
            Integer question_count = part.getQuestion_count();

            if ((type == null || "".equals(type))
                    && (difficulty == null || "".equals(difficulty))
                    && (grade == null || "".equals(grade))
                    && (question_count == null || question_count <= 0)) {
                continue;
            }

            int types = 0;
            if (type != null) {
                if ("选择题".equals(type)) {
                    types = 0;
                } else if ("填空题".equals(type)) {
                    types = 1;
                } else if ("问答题".equals(type)) {
                    types = 2;
                }
            }

            int diffi = 0;
            if (difficulty != null) {
                if ("简单".equals(difficulty)) {
                    diffi = 0;
                } else if ("较难".equals(difficulty)) {
                    diffi = 1;
                } else if ("很难".equals(difficulty)) {
                    diffi = 2;
                }
            }

            int grades = 0;
            if (grade != null) {
                if ("一年级".equals(grade)) {
                    grades = 0;
                } else if ("二年级".equals(grade)) {
                    grades = 1;
                } else if ("三年级".equals(grade)) {
                    grades = 2;
                } else if ("四年级".equals(grade)) {
                    grades = 3;
                } else if ("五年级".equals(grade)) {
                    grades = 4;
                } else if ("六年级".equals(grade)) {
                    grades = 5;
                } else if ("初一".equals(grade)) {
                    grades = 6;
                } else if ("初二".equals(grade)) {
                    grades = 7;
                } else if ("初三".equals(grade)) {
                    grades = 8;
                }
            }

            ExamPart examPart = new ExamPart();
            examPart.setType(types);
            examPart.setDifficulty(diffi);
            examPart.setGrade(grades);
            examPart.setQuestionCount(question_count);
            examParts.add(examPart);
            flag = true;
        }

        if (!flag) {
            return false;
        }

        // 添加练习
        this.insert(id);

        // 获取练习的组成部分，添加到数据库，并根据组成部分的策略设置，随机抽取题目
        Integer curr = db.getCurrKey("PRACTICE_ID");

        // 练习组成部分数组
        if (examParts != null && examParts.size() > 0) {
            for (int i = 0; i < parts.length; i++) {
                // 为这部分组成设置所属的练习ID
                examParts.get(i).setExampaperId(curr);
                examParts.get(i).setUserId(id);

                // 根据组成部分的特性，去错题列表挑选错题
                List<Integer> ids = this.practiceDao.getTeacherPartQuestion(examParts.get(i));

                // ids是所有符合条件的题目，我们从里面筛选出部分题,组成练习
                int truSize = ids.size();
                int needSize = examParts.get(i).getQuestionCount();
                List<Integer> trueId = new ArrayList<>();
                if (needSize < truSize) {
                    for (int j = 0; j < needSize; j++) {
                        Random random = new Random();
                        while (true) {
                            int i1 = ids.get(random.nextInt(truSize));
                            if (!trueId.contains(i1)) {
                                trueId.add(i1);
                                break;
                            }
                        }
                    }
                } else {
                    trueId = ids;
                    examParts.get(i).setQuestionCount(ids.size());
                }

                // 将这部分组成添加到练习中
                this.addPart(examParts.get(i));

                // 将上述错题添加到练习错题列表中
                // 获取组成部分id
                Integer pid = db.getCurrKey("PART_ID");
                for (Integer ida : trueId) {
                    this.practiceDao.addPartQuestion(pid, ida);
                }
            }
        }

        return false;
    }


    @Override
    public RespInfo queryPraUnderTea(PracticeReq req, Integer id) {
        req.valiation();

        ExamPaper examPaper = new ExamPaper();
        BeanUtils.copyProperties(req, examPaper);
        examPaper.setUserId(id);


        PageHelper.startPage(req.getPage(), req.getLimit());
        List<ExamPaper> list = this.practiceDao.queryPraUnderTea(examPaper);
        PageInfo<ExamPaper> pageInfo = new PageInfo<>(list);


        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }
}