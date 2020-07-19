package com.five.questionSystem.common;

import com.five.questionSystem.vo.RespInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 不同的异常类型，响应给浏览器不同的提示信息
 **/
@ControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private RespInfo respInfo;


    /**
     * 系统内部出现异常时，响应给浏览器的信息
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RespInfo HandlerBusinessException(BusinessException ex) {
        respInfo.setCode(-1);
        respInfo.setData("系统出现内部异常:" + ex.getMessage());
        return respInfo;
    }


    /**
     * 用户越过权限访问时，响应给浏览器的信息
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public RespInfo HandlerUnauthorizedException(UnauthorizedException ex) {
        respInfo.setCode(-1);
        respInfo.setData("用户权限受限制:" + ex.getMessage());
        return respInfo;
    }


    /**
     * 用户身份验证失败时，响应给浏览器的信息
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public RespInfo HandlerAuthenticationException(AuthenticationException ex) {
        respInfo.setData("用户身份验证失败:" + ex.getMessage());
        return respInfo;
    }


    /**
     * 用户账号被禁用
     */
    @ExceptionHandler(UserDisableException.class)
    @ResponseBody
    public RespInfo HandlerUserDisableException(UserDisableException ex) {
        respInfo.setCode(-1);
        respInfo.setData(ex.getMessage());
        return respInfo;
    }
}
