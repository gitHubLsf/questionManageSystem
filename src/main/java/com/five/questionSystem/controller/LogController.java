package com.five.questionSystem.controller;

import com.five.questionSystem.common.Logs;
import com.five.questionSystem.entity.Log;
import com.five.questionSystem.service.LogService;
import com.five.questionSystem.vo.LogReq;
import com.five.questionSystem.vo.RespInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * 日志管理类
 */
@Controller
@RequestMapping("/log")
public class LogController {

    /**
     * 根据日志id查询日志
     */
    @RequestMapping("{id}")
    @ResponseBody
    public ModelAndView queryById(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Log log = logService.queryById(id);
        modelAndView.addObject("log", log);
        modelAndView.setViewName("log/admin/logDetail");
        return modelAndView;
    }


    /**
     * 查询所有日志
     */
    @RequestMapping("/list")
    @ResponseBody
    public RespInfo queryPage(LogReq req) {
        return logService.queryPage(req);
    }


    /**
     * 根据id删除日志
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Logs(operationName = "删除日志", operationType = "delete_log")
    public RespInfo delete(Integer id) {
        logService.deleteById(id);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 批量删除日志
     */
    @RequestMapping("/batchDelete")
    @ResponseBody
    @Logs(operationName = "批量删除日志", operationType = "batchDel_log")
    public RespInfo batchDelete(Integer[] ids) {
        logService.batchDelete(ids);
        respInfo.setCode(0);
        return respInfo;
    }


    @Autowired
    private LogService logService;

    @Autowired
    private RespInfo respInfo;
}