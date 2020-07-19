package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    private static final long serialVersionUID = -50413681831626659L;

    private Integer id;
    private String name;
    private Integer type;
    private Integer board;
    private Integer difficulty;
    private Integer grade;
    private String hashCode;
    private List<Option> ops;
    private Integer questionId;
    private String userName;
    private Integer userId;
}