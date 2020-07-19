package com.five.questionSystem.dao;

import com.five.questionSystem.entity.Role;
import com.five.questionSystem.entity.User;
import com.five.questionSystem.vo.NewRole;
import com.five.questionSystem.vo.UserUpdateReq;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;


public interface UserDao {

    /**
     * 根据用户 id 查询用户信息
     */
    User queryById(@Param("id") int id);


    List<User> queryAll(User user);


    /**
     * 添加用户
     */
    void insert(User user);


    /**
     * 修改用户信息
     */
    void update(User user);


    /**
     * 删除用户
     */
    void deleteById(@Param("id") int id);


    /**
     * 批量删除用户
     */
    void deleteByIds(Integer[] ids);


    void addRoleByUserId(ArrayList<NewRole> list);


    void deleteUserRole(@Param("id") Integer id);


    /**
     * 根据用户名 userName 查询用户
     */
    User queryByName(String userName);


    /**
     * 禁用用户名为 userName 的账号
     */
    void disableUser(String userName);


    /**
     * 查询编号为 id 的用户的老师信息
     */
    User queryUserTeacher(@Param("id") int id);


    /**
     * 查询系统中所有用户的信息
     */
    List<User> getAllTeacher();


    /**
     * 添加学生和老师的关系
     */
    void insertUserTeacher(UserUpdateReq req);


    /**
     * 修改学生和老师的关系
     */
    void updateUserTeacher(UserUpdateReq req);


    List<Role> queryByUserId(@Param("id") Integer id);
}