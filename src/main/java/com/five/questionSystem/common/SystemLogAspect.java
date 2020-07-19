package com.five.questionSystem.common;

import com.five.questionSystem.entity.Log;
import com.five.questionSystem.entity.User;
import com.five.questionSystem.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 切面类,主要用于日志记录
 */
@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private LogService logService;


    /**
     * 切入表达式
     */
    @Pointcut("execution(* com.five.questionSystem.controller..*.*(..))")
    public void pointcut() {
    }


    /**
     * 前置通知
     *
     * @param joinPoint 切点
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
    }


    /**
     * 后置通知
     *
     * @param joinPoint 切点
     */
    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        try {
            //读取session中的用户
            User user = (User) session.getAttribute("userInfo");
            if (user == null) {
                //如果读不到用户，说明用户未登录或者登录失败
                user = new User();
                user.setUserName("未知用户");
            }

            //请求的IP
            String ip = request.getRemoteAddr();
            //操作人员名称
            String userName = user.getUserName();
            //请求的目标方法名
            String methodName = joinPoint.getSignature().getName();
            //请求的目标方法的参数列表
            Object[] arguments = joinPoint.getArgs();

            //判断目标方法是否有Logs注解标记，如果有，则获取注解的信息
            //目标方法所在类的类名
            String targetName = joinPoint.getTarget().getClass().getName();
            //类的类类型
            Class targetClass = Class.forName(targetName);
            //类下拥有的所有方法对象
            Method[] methods = targetClass.getMethods();

            //执行的操作类型，比如add操作
            String operationType = "";
            //执行的具体操作，比如添加用户
            String operationName = "";
            //遍历类下所有的方法对象，找到请求的目标方法对象，判断其是否有注解Logs标记
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        if (method.isAnnotationPresent(Logs.class)) {
                            operationType = method.getAnnotation(Logs.class).operationType();
                            operationName = method.getAnnotation(Logs.class).operationName();
                            break;
                        }

                    }
                }
            }

            //创建数据库日志
            Log log = new Log();
            log.setLogType(0);
            log.setVisitTime(new Date());
            log.setCreateBy(userName);
            log.setIp(ip);
            log.setDescription(operationName);
            log.setMethod(targetName + "." + methodName + "()." + operationType);

            //保存日志到数据库
            logService.insert(log);

        } catch (Exception e) {

        }
    }


    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        //读取session中的用户
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            user = new User();
            user.setUserName("未知用户");
        }

        try {
            //操作人员名称
            String userName = user.getUserName();
            //请求ip
            String ip = request.getRemoteAddr();
            //请求的目标方法名
            String methodName = joinPoint.getSignature().getName();
            //请求的目标方法的参数列表
            Object[] arguments = joinPoint.getArgs();
            //目标方法所在类的类名
            String targetName = joinPoint.getTarget().getClass().getName();
            //目标方法所在类的类类型
            Class targetClass = Class.forName(targetName);
            //目标方法所在类的所有方法对象
            Method[] methods = targetClass.getMethods();

            //执行的操作类型，比如add操作
            String operationType = "";
            //执行的具体操作，比如添加用户
            String operationName = "";
            //遍历目标方法所在类的所有方法对象，找到用户请求的目标方法对象，判断其是否有Logs注解标记
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(Logs.class).operationType();
                        operationName = method.getAnnotation(Logs.class).operationName();
                        break;
                    }
                }
            }

            //创建数据库异常日志
            Log log = new Log();

            //异常日志的类型用1标记
            log.setLogType(1);
            log.setVisitTime(new Date());
            log.setCreateBy(userName);
            log.setIp(ip);
            log.setDescription(operationName);
            //出现异常的目标方法的信息
            log.setMethod(targetName + "." + methodName + "()." + operationType);
            //抛出的异常类的名称
            log.setExceptionCode(e.getClass().getName());
            //抛出的异常的信息
            log.setExceptionDetail(e.getMessage());

            //保存异常日志到数据库中
            logService.insert(log);

        } catch (Exception ex) {

        }
    }
}
