package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;


/**
 * 用户登入时的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    //用户名
    private String userName;
    //密码
    private String password;
    //验证码
    private String verCode;
    //记住密码
    private String remember;


    public void validation() {
        if (!StringUtils.isEmpty(userName)) {
            userName = userName.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(password)) {
            password = password.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(verCode)) {
            verCode = verCode.replaceAll("\\s*", "");
        }
    }
}
