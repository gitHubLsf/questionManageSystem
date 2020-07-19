package com.five.questionSystem.dao;


public interface SequenceDao {

    Integer getKey(String name);


    Integer getCurrKey(String name);
}