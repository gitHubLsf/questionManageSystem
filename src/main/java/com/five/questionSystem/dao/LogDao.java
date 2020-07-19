package com.five.questionSystem.dao;

import com.five.questionSystem.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface LogDao {

    /**
     * 根据日志id查询日志
     */
    Log queryById(@Param("id") int id);


    /**
     * 查询所有日志
     */
    List<Log> queryAll(Log log);


    /**
     * 添加日志
     */
    void insert(Log log);


    /**
     * 根据id删除日志
     */
    void deleteById(@Param("id") int id);


    /**
     * 批量删除日志
     */
    void batchDelete(Integer[] ids);
}