package com.five.questionSystem.common;

import com.five.questionSystem.dao.SequenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DBUtils {

    @Autowired
    private SequenceDao sequenceDao;

    public Integer getKey(String name) {
        return sequenceDao.getKey(name);
    }

    public Integer getCurrKey(String name) {
        return sequenceDao.getCurrKey(name);
    }

}
