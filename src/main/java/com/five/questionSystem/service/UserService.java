package com.five.questionSystem.service;


import com.five.questionSystem.entity.User;
import com.five.questionSystem.vo.*;

import java.util.List;


public interface UserService {

    /**
     * 根据 id 查询用户
     */
    User queryById(Integer id);


    /**
     * 添加用户
     */
    void insert(UserAddReq user);


    /**
     * 管理员修改用户信息
     */
    void update(UserUpdateReq req);


    /**
     * 删除用户
     */
    void deleteById(Integer id);


    /**
     * 根据用户名 userName 查询用户
     */
    User queryByName(String userName);


    RespInfo queryPage(UserReq req);


    /**
     * 批量删除用户
     */
    void deleteByIds(Integer[] ids);


    List<User> queryAll(User user);


    /**
     * 用户修改个人信息
     */
    void updatePersonInfo(UserUpdateReq req);


    User UserLogin(String userName);


    /**
     * 禁用用户名为 userName 的账号
     */
    void disableUser(String userName);


    /**
     * 查询编号为 id 的用户的老师
     */
    User queryUserTeacher(Integer id);


    /**
     * 查询系统中所有老师的信息
     */
    List<User> getAllTeacher();
}