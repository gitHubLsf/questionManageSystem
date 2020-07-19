package com.five.questionSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 109487841665718711L;

    private Integer id;
    //用户名
    private String userName;
    //密码
    private String password;
    //性别
    private Integer sex;
    //手机
    private String phone;
    //邮箱
    private String email;
    //拥有的角色
    private List<Role> roleList;
    //账号状态(0:禁用,1:启用)
    private Integer status;
    private String teacherName;
    private List<User> teacherList;
    private Integer teacherId;
    private Integer role;

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}