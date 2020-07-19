package com.five.questionSystem.service;


import com.five.questionSystem.vo.Types;

import java.util.List;

public interface CountService {

    List<Types> querySystemInfo();


    List<Types> querySystemQuestionNumByType();


    List<Types> querySystemQuestionNumByDifficulty();


    List<Types> querySystemQuestionNumByGrade();


    int queryStudentNumUnderTeacher(int id);


    List<Types> queryQuestionNumByTypeUnderTeacher(int id);


    List<Types> queryQuestionNumByTypeUnderStudent(int id);


    List<Types> queryQuestionNumByDifficultyUnderStudent(int id);


    List<Types> queryStudentInfo(int id);


    int queryQuestionNumUnderTeacher(int id);


    List<Types> queryQuestionNumByDifficultyUnderTeacher(int id);


    List<Types> queryQuestionNumEveryStudentUnderTeacher(int id);
}
