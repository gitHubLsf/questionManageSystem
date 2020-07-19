package com.five.questionSystem.dao;

import com.five.questionSystem.entity.Option;
import com.five.questionSystem.entity.Question;
import com.five.questionSystem.entity.PersonQuestion;
import com.five.questionSystem.vo.Search;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuestionDao {

    /**
     * 根据 id 查询系统题库中的某道错题信息
     */
    Question querySysQuestionById(@Param("id") int id);


    /**
     * 查看题库中的所有错题
     */
    List<Question> querySystemQuestion(Question question);


    /**
     * 添加错题到系统题库
     */
    void insertQuestionToSystem(Question question);


    /**
     * 修改系统题库中的某道错题
     */
    void updateSysQuestion(Question question);


    /**
     * 删除题库中的某道错题
     */
    int deleteSysQuestionById(@Param("id") int id);


    /**
     * 批量删除系统题库中的错题
     */
    void batchDeleteSysQuestion(Integer[] ids);


    /**
     * 查看题库中是否有某道题
     */
    Question hasQuestionInSystem(String hashName);


    /**
     * 用户查看自己的错题
     */
    List<Question> queryPersonQuestion(PersonQuestion question);


    /**
     * 用户删除自己的错题
     */
    void deletePersonQuestion(@Param("id") int id);


    /**
     * 用户批量删除自己的错题
     */
    void batchDeletePersonQuestion(Integer[] ids);


    /**
     * 用户查询自己的某道错题
     */
    Question queryPersonQuestionById(@Param("id") Integer id);


    /**
     * 用户修改自己的错题
     */
    void updatePersonQuestion(PersonQuestion question);


    /**
     * 用户添加错题到自己的题库
     */
    void addQuestionByPerson(PersonQuestion personQuestion);


    /**
     * 查看自己的题库中是否有某道题
     */
    PersonQuestion hasPersonQuestion(Search search);


    /**
     * 教师查看手下学生的全部错题
     */
    List<Question> queryStuQuestionUnderTea(PersonQuestion question);


    Option select1(Integer id);

}