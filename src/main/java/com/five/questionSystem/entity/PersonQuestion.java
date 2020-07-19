package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonQuestion {
    private Integer id;
    private Integer userId;
    private Integer questionId;
    private Integer difficulty;
    private Integer grade;
    private Integer type;
    private String name;
}
