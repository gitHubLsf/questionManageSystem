package com.five.questionSystem.service;

import com.five.questionSystem.entity.Question;
import com.five.questionSystem.entity.PersonQuestion;
import com.five.questionSystem.vo.*;

import java.util.List;


public interface QuestionService {

    /**
     * 添加错题到系统题库
     */
    boolean insertQuestionToSys(Question question);


    /**
     * 修改系统中的某道错题
     */
    void updateSysQuestion(QuesUpdateReq req);


    /**
     * 删除题库中的某道错题
     */
    void deleteSysQuestionById(Integer id);


    /**
     * 查看题库所有错题
     */
    RespInfo querySystemQuestion(QuesReq req);


    /**
     * 批量删除题库中的错题
     */
    void batchDeleteSysQuestion(Integer[] ids);


    /**
     * 用户查看自己的错题
     */
    RespInfo queryPersonQuestion(QuesReq req, Integer currentId);


    /**
     * 用户删除自己的错题
     */
    void deletePersonQuestion(Integer id);


    /**
     * 用户批量删除自己的错题
     */
    void batchDeletePersonQuestion(Integer[] ids);


    /**
     * 用户查询自己的某道错题
     */
    Question queryPersonQuestionById(Integer id);


    /**
     * 用户修改自己的错题
     */
    void updatePersonQuestion(QuesUpdateReq req);


    /**
     * 用户添加错题到题库（加到题库和个人题库）
     */
    boolean addQuestionByPerson(QuestionAddReq req, Integer id);


    /**
     * 教师查看手下学生的全部错题
     */
    RespInfo queryStuQuestionUnderTea(QuesReq req, Integer id);


    /**
     * 用户抽取抽题组成练习
     */
    void chooseQuestionToPractice(Integer[] ids, Integer userId);


    /**
     * 教师从题库中选题导入到自己的题库中
     */
    void teacherImportQuesFromSys(Integer[] ids, Integer id);


    /**
     * 根据 id 查询系统题库中的某道错题信息
     */
    Question querySysQuestionById(Integer id);

}