package com.five.questionSystem.controller;

import com.five.questionSystem.common.Logs;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ErrorController {

    @RequestMapping("/*")
    @Logs(operationName = "非法的url请求", operationType = "error_request")
    public ModelAndView error() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tips/404");
        return modelAndView;
    }
}
