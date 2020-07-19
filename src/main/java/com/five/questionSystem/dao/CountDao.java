package com.five.questionSystem.dao;


import com.five.questionSystem.vo.Search;
import com.five.questionSystem.vo.Types;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CountDao {
    int querySystemQuestionNumByType(@Param("id") Integer id);

    int querySystemQuestionNumByDifficulty(@Param("id") Integer id);

    int querySystemQuestionNumByGrade(@Param("id") Integer id);

    int querySystemStudentNum();

    int querySystemExamNum();

    int querySystemTeacherNum();

    int querySystemLogNum();

    int querySystemQuestionNum();


    int queryStudentNumUnderTeacher(@Param("id") int id);

    int queryQuestionNumByTypeUnderTeacher(Search search);

    int queryQuestionNumByTypeUnderStudent(Search search);


    int queryQuestionNumByDifficultyUnderStudent(Search search);


    Integer queryStudentQuestionNum(@Param("id") int id);

    Integer queryStudentOwnExamNum(@Param("id") int id);

    Integer queryStudentTeacherExamNum(@Param("id") int id);

    int queryQuestionNumUnderTeacher(@Param("id") int id);


    List<Types> queryQuestionNumEveryStudentUnderTeacher(@Param("id") int id);

    int queryQuestionNumByDifficultyUnderTeacher(Search search);
}
