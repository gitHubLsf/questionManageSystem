package com.five.questionSystem.vo;

import com.github.pagehelper.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    private Integer limit;
    private Integer page;
    private String userName;
    private String password;
    private Integer sex;
    private String phone;
    private String email;
    private Integer status;


    public void valiation() {
        if (Objects.isNull(page) || page < 1) {
            page = 1;
        }
        if (Objects.isNull(limit) || limit < 5) {
            limit = 10;
        }
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
