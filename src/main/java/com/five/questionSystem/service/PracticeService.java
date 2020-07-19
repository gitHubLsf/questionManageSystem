package com.five.questionSystem.service;

import com.five.questionSystem.entity.ExamPaper;
import com.five.questionSystem.entity.ExamPart;
import com.five.questionSystem.vo.PracticeReq;
import com.five.questionSystem.vo.ExamPartReq;
import com.five.questionSystem.vo.RespInfo;


public interface PracticeService {

    /**
     * 根据 id 查询练习
     */
    ExamPaper queryById(Integer id);


    /**
     * 删除练习
     */
    void deleteById(int id);


    /**
     * 批量删除练习
     */
    void batchDelete(Integer[] ids);


    /**
     * 管理员查看系统练习库
     */
    RespInfo querySysPractice(PracticeReq practiceReq);


    boolean add(ExamPartReq[] parts);


    /**
     * 编号为 id 的用户添加练习
     */
    void insert(int id);


    ExamPaper getById(Integer id);


    /**
     * 根据 id 查询练习信息
     */
    ExamPaper queryPracticeById(Integer id);


    /**
     * 修改练习名称
     */
    void update(PracticeReq req);


    /**
     * 学生或教师查看自己创建的练习
     */
    RespInfo queryPersonPractice(PracticeReq practiceReq, int id);


    /**
     * 教师添加练习给学生
     */
    boolean teacherAddPractice(ExamPartReq[] parts, Integer id);


    /**
     * 学生查看老师布置的练习
     */
    RespInfo queryPraUnderTea(PracticeReq practiceReq, Integer id);


    void addPart(ExamPart part);
}