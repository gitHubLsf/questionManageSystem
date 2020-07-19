package com.five.questionSystem.service.impl;

import com.five.questionSystem.entity.Option;
import com.five.questionSystem.dao.OptionDao;
import com.five.questionSystem.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Override
    public void insert(List<Option> ops) {
        optionDao.insert(ops);
    }
}