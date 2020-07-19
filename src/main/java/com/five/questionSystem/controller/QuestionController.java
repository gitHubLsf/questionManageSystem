package com.five.questionSystem.controller;

import com.five.questionSystem.common.DBUtils;
import com.five.questionSystem.common.Logs;
import com.five.questionSystem.common.OCRUtil;
import com.five.questionSystem.entity.Option;
import com.five.questionSystem.entity.Question;
import com.five.questionSystem.entity.User;
import com.five.questionSystem.service.OptionService;
import com.five.questionSystem.service.QuestionService;
import com.five.questionSystem.service.impl.UserServiceImpl;
import com.five.questionSystem.vo.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 错题管理类
 */
@Controller
@RequestMapping("/question")
public class QuestionController {


    /**
     * 用户查看自己的某道错题，只查看不修改
     */
    @RequestMapping("{id}")
    @ResponseBody
    public ModelAndView queryPersonQuestionInfo(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.queryPersonQuestionById(id);
        modelAndView.addObject("ques", question);
        modelAndView.setViewName("question/questionDetail");
        return modelAndView;
    }


    /**
     * 教师查看手下某个学生的某道错题
     */
    @RequestMapping("/underTea/question/{id}")
    @ResponseBody
    public ModelAndView queryStuQuesUnderTea(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.queryPersonQuestionById(id);
        //查询这道错题的用户的名称
        User user = userService.queryById(question.getUserId());
        question.setUserName(user.getUserName());
        modelAndView.addObject("ques", question);
        modelAndView.setViewName("question/teacher/stuQuesUnderTeaDetail");
        return modelAndView;
    }


    /**
     * 根据 id 查看题库中的某道错题,不可编辑
     */
    @RequestMapping("/system/{id}")
    @ResponseBody
    public ModelAndView querySystemQuestion(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.querySysQuestionById(id);
        modelAndView.addObject("ques", question);
        modelAndView.setViewName("question/questionDetail");
        return modelAndView;
    }


    /**
     * 用户查询自己的某道错题信息，并修改
     */
    @RequestMapping("/update/{id}")
    public ModelAndView queryPersonQuestionById(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.queryPersonQuestionById(id);
        modelAndView.addObject("ques", question);
        modelAndView.setViewName("question/questionEdit");
        return modelAndView;
    }


    /**
     * 根据 id 查看题库中的某道错题
     */
    @RequestMapping("/system/update/{id}")
    public ModelAndView querySysQuestionById(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.querySysQuestionById(id);
        modelAndView.addObject("ques", question);
        modelAndView.setViewName("question/questionEdit");
        return modelAndView;
    }


    /**
     * 查看题库所有错题
     */
    @RequestMapping("/list/system")
    @ResponseBody
    public RespInfo querySystemQuestion(QuesReq req) {
        return questionService.querySystemQuestion(req);
    }


    /**
     * 用户查看自己的错题
     */
    @RequestMapping("/list/person")
    @ResponseBody
    public RespInfo queryPersonQuestion(QuesReq req, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        return questionService.queryPersonQuestion(req, user.getId());
    }


    /**
     * 教师查看手下学生的错题列表
     */
    @RequestMapping("/list/under/teacher")
    @ResponseBody
    public RespInfo queryStuQuestionUnderTea(QuesReq req, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        return questionService.queryStuQuestionUnderTea(req, user.getId());
    }


    /**
     * 删除题库中的某道错题
     */
    @RequestMapping("/delete/system")
    @ResponseBody
    @Logs(operationName = "删除错题", operationType = "delete_question")
    public RespInfo deleteSysQuestionById(Integer id) {
        questionService.deleteSysQuestionById(id);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 批量删除题库中的错题
     */
    @RequestMapping("/batchDelete/system")
    @ResponseBody
    @Logs(operationName = "批量删除错题", operationType = "batchDelete_question")
    public RespInfo batchDeleteSysQuestion(Integer[] ids) {
        questionService.batchDeleteSysQuestion(ids);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户删除自己的错题
     */
    @RequestMapping("/delete/person")
    @ResponseBody
    @Logs(operationName = "删除错题", operationType = "delete_question")
    public RespInfo deletePersonQuestion(Integer id) {
        questionService.deletePersonQuestion(id);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户批量删除自己的部分错题
     */
    @RequestMapping("/batchDelete/person")
    @ResponseBody
    @Logs(operationName = "批量删除错题", operationType = "batchDelete_question")
    public RespInfo batchDeletePersonQuestion(Integer[] ids) {
        questionService.batchDeletePersonQuestion(ids);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户修改自己的错题
     */
    @RequestMapping("/update/person")
    @ResponseBody
    @Logs(operationName = "修改错题", operationType = "edit_question")
    public RespInfo updatePersonQuestion(QuesUpdateReq req) {
        questionService.updatePersonQuestion(req);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 修改题库中的某道错题
     */
    @RequestMapping("/system/update")
    @ResponseBody
    @Logs(operationName = "修改错题", operationType = "edit_question")
    @RequiresRoles(value = "admin")
    @RequiresPermissions("edit_question")
    public RespInfo updateSysQuestion(QuesUpdateReq req) {
        questionService.updateSysQuestion(req);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 学生从Word文档批量导入错题
     */
    @RequestMapping(value = "/student/import")
    @ResponseBody
    @Logs(operationName = "从Word文档导入错题", operationType = "add_question_from_word")
    public RespInfo addQuestionByStudentFromFile(MultipartFile file, HttpServletRequest request) {
        respInfo.setCode(-1);

        User user = (User) request.getSession().getAttribute("userInfo");
        QuestionAddReq req = new QuestionAddReq();

        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            List<XWPFTable> tables = document.getTables();

            //每个table是一道题，循环添加到数据库
            for (XWPFTable table : tables) {
                // 获取表格的行
                List<XWPFTableRow> rows = table.getRows();

                //读取题目
                String name = rows.get(0).getCell(1).getText();
                if (Objects.isNull(name) || "".equals(name = name.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("题目不能为空");
                    return respInfo;
                }
                req.setName(name);

                //题目类型
                String type = rows.get(1).getCell(1).getText();
                if (type == null || "".equals(type = type.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("类型不能为空");
                    return respInfo;
                }
                int typeInt = 0;
                if ("选择题".equals(type)) {
                    typeInt = 0;
                } else if ("填空题".equals(type)) {
                    typeInt = 1;
                } else if ("问答题".equals(type)) {
                    typeInt = 2;
                } else {
                    respInfo.setMsg("类型非法");
                    return respInfo;
                }
                req.setType(typeInt);


                //难易度
                String diffi = rows.get(2).getCell(1).getText();
                if (diffi == null || "".equals(diffi = diffi.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("难度不能为空");
                    return respInfo;
                }
                int diffInt = 0;
                if ("简单".equals(diffi)) {
                    diffInt = 0;
                } else if ("较难".equals(diffi)) {
                    diffInt = 1;
                } else if ("很难".equals(diffi)) {
                    diffInt = 2;
                } else {
                    respInfo.setMsg("难度非法");
                    return respInfo;
                }
                req.setDifficulty(diffInt);


                //如果是选择题，获取并添加选项
                ArrayList<Option> ops = new ArrayList<>(4);
                String regx = "^[A-D]{1}:{1}\\w+";
                if (typeInt == 0) {
                    if (rows.size() < 7) {
                        respInfo.setMsg("选择题选项个数有误");
                        return respInfo;
                    }
                    for (int i = 3; i < 7; i++) {
                        Option op = new Option();

                        //获取选项内容
                        String text = rows.get(i).getCell(1).getText();
                        if (text == null || "".equals(text = text.replaceAll("\\s*", ""))) {
                            respInfo.setMsg("选项不能为空");
                            return respInfo;
                        }

                        //判断选项内容是否符合要求
                        if (!text.matches(regx)) {
                            respInfo.setMsg("选项不符合格式");
                            return respInfo;
                        }

                        op.setOpt(text);
                        ops.add(op);
                    }
                }

                //添加题目到题库(个人题库和系统题库)
                boolean flag = questionService.addQuestionByPerson(req, user.getId());
                if (flag) {
                    //添加选项
                    if (typeInt == 0) {
                        for (Option op : ops) {
                            op.setQuestionId(db.getCurrKey("QUESTION_ID"));
                        }
                        optionService.insert(ops);
                    }
                }
            }
            respInfo.setCode(0);
            document.close();
            return respInfo;
        } catch (Exception e) {
            respInfo.setMsg("表格格式不符合要求");
            return respInfo;
        }
    }


    /**
     * 老师从Word文档批量导入错题
     */
    @RequestMapping(value = "/teacher/import")
    @ResponseBody
    @Logs(operationName = "从Word文档导入错题", operationType = "add_question_from_word")
    public RespInfo addQuestionByTeacherFromFile(MultipartFile file, HttpServletRequest request) {
        respInfo.setCode(-1);

        User user = (User) request.getSession().getAttribute("userInfo");
        QuestionAddReq req = new QuestionAddReq();

        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            List<XWPFTable> tables = document.getTables();

            //每个table是一道题，循环添加到数据库
            for (XWPFTable table : tables) {
                // 获取表格的行
                List<XWPFTableRow> rows = table.getRows();

                //读取题目
                String name = rows.get(0).getCell(1).getText();
                if (name == null || "".equals(name = name.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("题目不能为空");
                    return respInfo;
                }
                req.setName(name);

                //题目类型
                String type = rows.get(1).getCell(1).getText();
                if (type == null || "".equals(type = type.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("类型不能为空");
                    return respInfo;
                }
                int typeInt = 0;
                if ("选择题".equals(type)) {
                    typeInt = 0;
                } else if ("填空题".equals(type)) {
                    typeInt = 1;
                } else if ("问答题".equals(type)) {
                    typeInt = 2;
                } else {
                    respInfo.setMsg("类型非法");
                    return respInfo;
                }
                req.setType(typeInt);


                //适用年级
                String grade = rows.get(2).getCell(1).getText();
                if (grade == null || "".equals(grade = grade.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("适用年级不能为空");
                    return respInfo;
                }
                int gradeInt = 0;
                if ("一年级".equals(grade)) {
                    gradeInt = 1;
                } else if ("二年级".equals(grade)) {
                    gradeInt = 1;
                } else if ("三年级".equals(grade)) {
                    gradeInt = 2;
                } else if ("四年级".equals(grade)) {
                    gradeInt = 3;
                } else if ("五年级".equals(grade)) {
                    gradeInt = 4;
                } else if ("六年级".equals(grade)) {
                    gradeInt = 5;
                } else if ("初一".equals(grade)) {
                    gradeInt = 6;
                } else if ("初二".equals(grade)) {
                    gradeInt = 7;
                } else {
                    respInfo.setMsg("适用年级非法");
                    return respInfo;
                }
                req.setGrade(gradeInt);


                //难易度
                String diffi = rows.get(3).getCell(1).getText();
                if (diffi == null || "".equals(diffi = diffi.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("难度不能为空");
                    return respInfo;
                }
                int diffInt = 0;
                if ("简单".equals(diffi)) {
                    diffInt = 0;
                } else if ("较难".equals(diffi)) {
                    diffInt = 1;
                } else if ("很难".equals(diffi)) {
                    diffInt = 2;
                } else {
                    respInfo.setMsg("难度非法");
                    return respInfo;
                }
                req.setDifficulty(diffInt);


                //如果是选择题，获取并添加选项
                ArrayList<Option> ops = new ArrayList<>(4);
                String regx = "^[A-D]{1}:{1}\\w+";
                if (typeInt == 0) {
                    //获取questionID
                    if (rows.size() < 8) {
                        respInfo.setMsg("选择题选项个数有误");
                        return respInfo;
                    }
                    for (int i = 4; i < 8; i++) {
                        Option op = new Option();

                        //获取选项内容
                        String text = rows.get(i).getCell(1).getText();
                        if (text == null || "".equals(text = text.replaceAll("\\s*", ""))) {
                            respInfo.setMsg("选项不能为空");
                            return respInfo;
                        }
                        //判断选项内容是否符合要求
                        if (!text.matches(regx)) {
                            respInfo.setMsg("选项不符合格式");
                            return respInfo;
                        }
                        op.setOpt(text);
                        ops.add(op);
                    }
                }

                //添加题目题库(个人题库和系统题库)
                boolean flag = questionService.addQuestionByPerson(req, user.getId());
                if (flag) {
                    //添加选项
                    if (typeInt == 0) {
                        for (Option op : ops) {
                            op.setQuestionId(db.getCurrKey("QUESTION_ID"));
                        }
                        optionService.insert(ops);
                    }
                }
            }
            respInfo.setCode(0);
            document.close();
            return respInfo;
        } catch (Exception e) {
            respInfo.setMsg("表格格式不符合要求");
            return respInfo;
        }
    }


    /**
     * 管理员从Word文档批量导入错题
     */
    @RequestMapping(value = "/admin/import")
    @ResponseBody
    @Logs(operationName = "从Word文档导入错题", operationType = "add_question_from_word")
    public RespInfo adminBatchImport(MultipartFile file) {
        respInfo.setCode(-1);

        Question req = new Question();
        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            List<XWPFTable> tables = document.getTables();

            //每个table是一道题，循环添加到数据库
            for (XWPFTable table : tables) {
                // 获取表格的行
                List<XWPFTableRow> rows = table.getRows();

                //读取题目
                String name = rows.get(0).getCell(1).getText();
                if (name == null || "".equals(name = name.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("题目不能为空");
                    return respInfo;
                }
                req.setName(name);

                //题目类型
                String type = rows.get(1).getCell(1).getText();
                if (type == null || "".equals(type = type.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("类型不能为空");
                    return respInfo;
                }
                int typeInt = 0;
                if ("选择题".equals(type)) {
                    typeInt = 0;
                } else if ("填空题".equals(type)) {
                    typeInt = 1;
                } else if ("问答题".equals(type)) {
                    typeInt = 2;
                } else {
                    respInfo.setMsg("类型非法");
                    return respInfo;
                }
                req.setType(typeInt);


                //适用年级
                String grade = rows.get(2).getCell(1).getText();
                if (grade == null || "".equals(grade = grade.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("适用年级不能为空");
                    return respInfo;
                }
                int gradeInt = 0;
                if ("一年级".equals(grade)) {
                    gradeInt = 1;
                } else if ("二年级".equals(grade)) {
                    gradeInt = 1;
                } else if ("三年级".equals(grade)) {
                    gradeInt = 2;
                } else if ("四年级".equals(grade)) {
                    gradeInt = 3;
                } else if ("五年级".equals(grade)) {
                    gradeInt = 4;
                } else if ("六年级".equals(grade)) {
                    gradeInt = 5;
                } else if ("初一".equals(grade)) {
                    gradeInt = 6;
                } else if ("初二".equals(grade)) {
                    gradeInt = 7;
                } else {
                    respInfo.setMsg("适用年级非法");
                    return respInfo;
                }
                req.setGrade(gradeInt);


                //难易度
                String diffi = rows.get(3).getCell(1).getText();
                if (diffi == null || "".equals(diffi = diffi.replaceAll("\\s*", ""))) {
                    respInfo.setMsg("难度不能为空");
                    return respInfo;
                }
                int diffInt = 0;
                if ("简单".equals(diffi)) {
                    diffInt = 0;
                } else if ("较难".equals(diffi)) {
                    diffInt = 1;
                } else if ("很难".equals(diffi)) {
                    diffInt = 2;
                } else {
                    respInfo.setMsg("难度非法");
                    return respInfo;
                }
                req.setDifficulty(diffInt);


                //如果是选择题，获取并添加选项
                ArrayList<Option> ops = new ArrayList<>(4);
                String regx = "^[A-D]{1}:{1}\\w+";
                if (typeInt == 0) {
                    //获取questionID
                    if (rows.size() < 8) {
                        respInfo.setMsg("选择题选项个数有误");
                        return respInfo;
                    }
                    for (int i = 4; i < 8; i++) {
                        Option op = new Option();

                        //获取选项内容
                        String text = rows.get(i).getCell(1).getText();
                        if (text == null || "".equals(text = text.replaceAll("\\s*", ""))) {
                            respInfo.setMsg("选项不能为空");
                            return respInfo;
                        }
                        //判断选项内容是否符合要求
                        if (!text.matches(regx)) {
                            respInfo.setMsg("选项不符合格式");
                            return respInfo;
                        }
                        op.setOpt(text);
                        ops.add(op);
                    }
                }

                //添加题目到系统题库
                boolean flag = questionService.insertQuestionToSys(req);
                if (flag) {
                    //添加选项
                    if (typeInt == 0) {
                        for (Option op : ops) {
                            op.setQuestionId(db.getCurrKey("QUESTION_ID"));
                        }
                        optionService.insert(ops);
                    }
                }
            }

            respInfo.setCode(0);
            document.close();
            return respInfo;
        } catch (Exception e) {
            respInfo.setMsg("表格格式不符合要求");
            return respInfo;
        }
    }


    /**
     * OCR 识别图片中的错题
     */
    @RequestMapping("/imgImport")
    @ResponseBody
    @Logs(operationName = "OCR文字识别错题", operationType = "add_question_from_ocr")
    public RespInfo imgImport(MultipartFile file, HttpServletRequest request) {
        respInfo.setCode(-1);

        try {
            // 获取 upload 目录的绝对路径 /upload
            String upload = request.getSession().getServletContext().getRealPath("/upload");

            // 获取上传文件的名称
            String originalFilename = file.getOriginalFilename();

            // 构造文件的绝对路径
            File tager = new File(upload, originalFilename);

            // 保存文件到 webapp 目录下的 upload 目录
            file.transferTo(tager);

            String path = upload + "/" + originalFilename;

            //提取中的文字
            String ocr = this.ocrUtil.ocr(path);

            respInfo.setCode(0);
            respInfo.setMsg(ocr);
        } catch (Exception e) {
        }
        return respInfo;
    }


    /**
     * 添加错题到系统题库
     */
    @RequestMapping(value = "/system/add")
    @ResponseBody
    @Logs(operationName = "添加错题", operationType = "add_question")
    public RespInfo addQuestionToSystem(Question question) {
        int flag = this.questionService.insertQuestionToSys(question) ? 0 : -1;
        respInfo.setCode(flag);
        return respInfo;
    }


    /**
     * 用户添加错题到自己的题库
     */
    @RequestMapping(value = "/person/add")
    @ResponseBody
    @Logs(operationName = "添加错题", operationType = "add_question")
    public RespInfo addQuestionByPerson(QuestionAddReq req, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        this.questionService.addQuestionByPerson(req, user.getId());
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户抽选错题组成练习
     */
    @RequestMapping("/choose")
    @ResponseBody
    @Logs(operationName = "抽题组建练习", operationType = "choose_question_to_practice")
    public RespInfo chooseQuestionToPractice(Integer[] ids, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        this.questionService.chooseQuestionToPractice(ids, user.getId());
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 教师导入题库中的题到自己的错题库中
     */
    @RequestMapping("/import/from/system")
    @ResponseBody
    @Logs(operationName = "导出题目", operationType = "output_question")
    public RespInfo teacherImportQuesFromSys(Integer[] ids, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        this.questionService.teacherImportQuesFromSys(ids, user.getId());
        respInfo.setCode(0);
        return respInfo;
    }


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private DBUtils db;

    @Autowired
    private OptionService optionService;

    @Autowired
    private OCRUtil ocrUtil;

    @Autowired
    private RespInfo respInfo;
}