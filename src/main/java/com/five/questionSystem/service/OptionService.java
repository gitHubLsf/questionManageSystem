package com.five.questionSystem.service;

import com.five.questionSystem.entity.Option;

import java.util.List;


public interface OptionService {

    /**
     * 添加选择题选项
     */
    void insert(List<Option> ops);

}