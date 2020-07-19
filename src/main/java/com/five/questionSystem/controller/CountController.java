package com.five.questionSystem.controller;


import com.five.questionSystem.entity.User;
import com.five.questionSystem.service.CountService;
import com.five.questionSystem.vo.RespInfo;
import com.five.questionSystem.vo.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;


/**
 * 数据统计分析类
 */
@Controller
@RequestMapping("/count")
public class CountController {

    @Autowired
    private CountService countService;

    @Autowired
    private RespInfo respInfo;


    /**
     * 管理员统计系统中不同难度的错题数量
     */
    @RequestMapping("/system/difficulty/num")
    @ResponseBody
    public RespInfo querySystemQuestionNumByDifficulty() {
        List<Types> list = countService.querySystemQuestionNumByDifficulty();
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 管理员统计系统中不同年级的错题数量
     */
    @RequestMapping("/system/grade/num")
    @ResponseBody
    public RespInfo querySystemQuestionNumByGrade() {
        List<Types> list = countService.querySystemQuestionNumByGrade();
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 管理员统计系统中不同类型的错题数量
     */
    @RequestMapping("/system/type/num")
    @ResponseBody
    public RespInfo querySystemQuestionNumByType() {
        List<Types> list = countService.querySystemQuestionNumByType();
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 管理员查看系统信息(错题个数,试卷个数,学生个数,老师个数,日志个数)
     */
    @RequestMapping("/system/info")
    @ResponseBody
    public RespInfo querySystemInfo() {
        List<Types> list = this.countService.querySystemInfo();
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 教师查看自己的学生的个数
     */
    @RequestMapping("/teacher/ownstudent/num")
    @ResponseBody
    public RespInfo queryStudentNumUnderTeacher(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        int num = this.countService.queryStudentNumUnderTeacher(user.getId());
        respInfo.setCode(0);
        respInfo.setData(num);
        return respInfo;
    }


    /**
     * 学生查看自己的教师底下的学生总数
     */
    @RequestMapping("/teacher/ownstudent/num/student")
    @ResponseBody
    public RespInfo queryStudentNumUnderTeacherByStudent(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        int teacherId = user.getTeacherId();
        int num = this.countService.queryStudentNumUnderTeacher(teacherId);
        respInfo.setCode(0);
        respInfo.setData(num);
        return respInfo;
    }


    /**
     * 教师查看手下学生不同类型的错题的个数
     */
    @RequestMapping("/teacher/ownstudent/type/num")
    @ResponseBody
    public RespInfo queryQuestionNumByTypeUnderTeacher(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        List<Types> list = countService.queryQuestionNumByTypeUnderTeacher(user.getId());
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 教师查看手下学生不同难度的错题的个数
     */
    @RequestMapping("/teacher/ownstudent/difficulty/num")
    @ResponseBody
    public RespInfo queryQuestionNumByDifficultyUnderTeacher(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        List<Types> list = countService.queryQuestionNumByDifficultyUnderTeacher(user.getId());
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 学生查看自己的不同类型的错题的个数
     */
    @RequestMapping("/student/ownquestion/type/num")
    @ResponseBody
    public RespInfo queryQuestionNumByTypeUnderStudent(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        List<Types> list = countService.queryQuestionNumByTypeUnderStudent(user.getId());
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 学生查看自己的不同难度的错题的个数
     */
    @RequestMapping("/student/ownquestion/difficulty/num")
    @ResponseBody
    public RespInfo queryQuestionNumByDifficultyUnderStudent(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        List<Types> list = countService.queryQuestionNumByDifficultyUnderStudent(user.getId());
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 学生查看自己的错题个数,自己的练习个数,老师创建的练习个数
     */
    @RequestMapping("/student/info")
    @ResponseBody
    public RespInfo queryStudentInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        List<Types> list = countService.queryStudentInfo(user.getId());
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 老师查看手下的学生的错题总数
     */
    @RequestMapping("/teacher/ownstudent/question/num")
    @ResponseBody
    public RespInfo queryQuestionNumUnderTeacher(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        int queryQuestionNumUnderTeacher = countService.queryQuestionNumUnderTeacher(user.getId());
        respInfo.setCode(0);
        respInfo.setData(queryQuestionNumUnderTeacher);
        return respInfo;
    }


    /**
     * 教师查看手下各个学生的错题数量
     */
    @RequestMapping("/teacher/everystudent/question/num")
    @ResponseBody
    public RespInfo queryQuestionNumEveryStudentUnderTeacher(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        List<Types> list = countService.queryQuestionNumEveryStudentUnderTeacher(user.getId());
        respInfo.setCode(0);
        respInfo.setData(list);
        return respInfo;
    }


    /**
     * 学生查看自己的错题数在老师手下学生的排名
     */
    @RequestMapping("/teacher/everystudent/question/num/sort")
    @ResponseBody
    public RespInfo queryQuestionNumEveryStudentUnderTeacherByStudent(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        int teacherId = user.getTeacherId();

        List<Types> list = countService.queryQuestionNumEveryStudentUnderTeacher(teacherId);
        Collections.sort(list);

        //学生查看自己的错题数
        List<Types> stu = countService.queryStudentInfo(user.getId());
        int stuNum = stu.get(0).getValue();

        int sort = 1;
        for (int i = 0; i < list.size(); i++) {
            if (stuNum <= list.get(i).getValue()) {
                break;
            }
            sort++;
        }

        respInfo.setCode(0);
        respInfo.setData(sort);
        return respInfo;
    }


    /**
     * 学生查看自己的老师手下的学生的错题总数
     */
    @RequestMapping("/teacher/ownstudent/question/num/student")
    @ResponseBody
    public RespInfo queryQuestionNumUnderTeacherByStudent(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        int teacherId = user.getTeacherId();
        int queryQuestionNumUnderTeacher = countService.queryQuestionNumUnderTeacher(teacherId);
        respInfo.setCode(0);
        respInfo.setData(queryQuestionNumUnderTeacher);
        return respInfo;
    }

}
