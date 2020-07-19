package com.five.questionSystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;


/**
 * 添加用户的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddReq {
    private String userName;
    private String password;
    private Integer sex;
    private String phone;
    private String email;
    private Integer role;

    public void volidation() {
        if (!StringUtils.isEmpty(userName)) {
            userName = userName.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(password)) {
            password = password.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(phone)) {
            phone = phone.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(email)) {
            email = email.replaceAll("\\s*", "");
        }
    }
}
