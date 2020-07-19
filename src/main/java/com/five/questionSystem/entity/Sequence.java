package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence implements Serializable {
    private static final long serialVersionUID = -47177712406505038L;

    private String sequenceName;
    private Integer sequenceValue;
}