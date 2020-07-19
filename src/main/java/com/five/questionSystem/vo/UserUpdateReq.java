package com.five.questionSystem.vo;

import com.github.pagehelper.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 修改用户信息的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateReq {
    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private Integer status;
    private Integer teacherId;


    public void volidation() {
        if (!StringUtil.isEmpty(userName)) {
            userName = userName.replaceAll("\\s*", "");
        }
        if (!StringUtil.isEmpty(password)) {
            password = password.replaceAll("\\s*", "");
        }
        if (!StringUtil.isEmpty(phone)) {
            phone = phone.replaceAll("\\s*", "");
        }
        if (!StringUtil.isEmpty(email)) {
            email = email.replaceAll("\\s*", "");
        }
    }
}
