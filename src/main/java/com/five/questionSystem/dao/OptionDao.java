package com.five.questionSystem.dao;

import com.five.questionSystem.entity.Option;

import java.util.List;

public interface OptionDao {

    /**
     * 添加选择题选项
     */
    void insert(List<Option> ops);

}