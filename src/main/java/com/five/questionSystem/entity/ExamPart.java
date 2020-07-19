package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamPart implements Serializable {
    private static final long serialVersionUID = 276722058175845289L;

    private Integer id;
    private String name;
    private Integer questionCount;
    private Integer exampaperId;
    private Integer grade;
    private Integer difficulty;
    private Integer board;
    private Integer type;
    private List<Question> questions;
    private Integer userId;
}