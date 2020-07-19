package com.five.questionSystem.common;

import java.lang.annotation.*;


/**
 * 自定义注解：方便我们自定义每个方法的日志记录内容
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logs {

    // 要执行的具体操作，比如添加用户
    String operationName() default "";

    // 要执行的操作的类型，比如 add_user
    String operationType() default "";

}