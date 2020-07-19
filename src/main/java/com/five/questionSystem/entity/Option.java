package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option implements Serializable {
    private static final long serialVersionUID = -72638529444678302L;
    
    private Integer id;
    private String opt;
    private Integer questionId;
}