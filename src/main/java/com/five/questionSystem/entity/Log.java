package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {
    private static final long serialVersionUID = 616577824299708186L;

    private Integer id;
    private Integer logType;
    private Date visitTime;
    private String createBy;
    private String url;
    private String ip;
    private String description;
    private Object executionTime;
    private String method;
    private String params;
    private String exceptionCode;
    private String exceptionDetail;
}