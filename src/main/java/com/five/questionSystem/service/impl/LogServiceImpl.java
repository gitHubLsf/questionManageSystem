package com.five.questionSystem.service.impl;

import com.five.questionSystem.common.DBUtils;
import com.five.questionSystem.entity.Log;
import com.five.questionSystem.dao.LogDao;
import com.five.questionSystem.service.LogService;
import com.five.questionSystem.vo.LogReq;
import com.five.questionSystem.vo.RespInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LogServiceImpl implements LogService {

    @Override
    public Log queryById(int id) {
        return logDao.queryById(id);
    }


    @Override
    public void insert(Log log) {
        log.setId(db.getKey("LOG_ID"));
        logDao.insert(log);
    }


    @Override
    public void deleteById(Integer id) {
        logDao.deleteById(id);
    }


    @Override
    public RespInfo queryPage(LogReq req) {
        req.volidation();

        Log log = new Log();
        BeanUtils.copyProperties(req, log);

        PageHelper.startPage(req.getPage(), req.getLimit());
        List<Log> list = this.logDao.queryAll(log);
        PageInfo<Log> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public void batchDelete(Integer[] ids) {
        logDao.batchDelete(ids);
    }


    @Autowired
    private LogDao logDao;

    @Autowired
    private DBUtils db;

    @Autowired
    private RespInfo respInfo;
}