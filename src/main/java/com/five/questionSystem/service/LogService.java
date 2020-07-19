package com.five.questionSystem.service;

import com.five.questionSystem.entity.Log;
import com.five.questionSystem.vo.LogReq;
import com.five.questionSystem.vo.RespInfo;


public interface LogService {

    /**
     * 根据日志id查询日志
     */
    Log queryById(int id);


    /**
     * 添加日志
     */
    void insert(Log log);


    /**
     * 查询所有日志
     */
    RespInfo queryPage(LogReq req);


    /**
     * 根据id删除日志
     */
    void deleteById(Integer id);


    /**
     * 批量删除日志
     */
    void batchDelete(Integer[] ids);
}