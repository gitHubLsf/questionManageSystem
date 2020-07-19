package com.five.questionSystem.service;

import com.five.questionSystem.entity.Role;
import com.five.questionSystem.vo.RespInfo;
import com.five.questionSystem.vo.RoleReq;
import com.five.questionSystem.vo.RoleUpdateReq;

import java.util.List;


public interface RoleService {

    /**
     * 根据角色 id 查询角色信息
     */
    Role queryRoleById(Integer id);


    /**
     * 修改角色信息
     */
    void update(RoleUpdateReq req);


    /**
     * 查询所有角色
     */
    RespInfo queryPage(RoleReq req);


    /**
     * 根据用户 id 查询角色信息
     */
    List<Role> queryByUserId(Integer id);
}