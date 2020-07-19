package com.five.questionSystem.dao;

import com.five.questionSystem.entity.ExamPaper;
import com.five.questionSystem.entity.ExamPart;
import com.five.questionSystem.entity.Option;
import com.five.questionSystem.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PracticeDao {


    ExamPaper queryById(@Param("id") Integer id);


    /**
     * 管理员查看系统所有练习
     */
    List<ExamPaper> querySysPractice(ExamPaper examPaper);


    /**
     * 修改练习名称
     */
    int update(ExamPaper examPaper);


    /**
     * 删除练习
     */
    int deleteById(@Param("id") int id);


    /**
     * 批量删除练习
     */
    void batchDelete(Integer[] ids);


    /**
     * 教师添加练习
     */
    void insert(ExamPaper req);


    List<Integer> getPartQuestion(ExamPart part);


    ExamPaper getById(@Param("id") Integer id);


    /**
     * 根据 id 查询练习信息
     */
    ExamPaper queryPracticeById(@Param("id") int id);


    /**
     * 学生或教师查看自己创建的练习
     */
    List<ExamPaper> queryPersonPractice(ExamPaper examPaper);


    /**
     * 学生查看老师布置的练习
     */
    List<ExamPaper> queryPraUnderTea(ExamPaper examPaper);


    List<ExamPart> getPartsByPaperId(@Param("id") int id);

    List<Option> select2(@Param("id") int id);

    List<Question> select1(@Param("id") int id);

    List<Integer> getTeacherPartQuestion(ExamPart part);

    void addPart(ExamPart part);

    void addPartQuestion(@Param("pid") Integer pid, @Param("id") Integer id);
}