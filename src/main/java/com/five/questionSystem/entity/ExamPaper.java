package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamPaper implements Serializable {
    private static final long serialVersionUID = -33090799369323159L;

    private Integer id;
    private String name;
    private Date createTime;
    private List<ExamPart> parts;
    private Integer userId;
    private String userName;
}