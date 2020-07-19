package com.five.questionSystem.dao;


import com.five.questionSystem.entity.Permission;
import com.five.questionSystem.entity.Role;
import com.five.questionSystem.vo.NewPermission;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;


public interface RoleDao {

    /**
     * 根据 id 查询角色信息
     */
    Role queryRoleById(@Param("id") int id);


    /**
     * 查询所有角色
     */
    List<Role> queryAll(Role role);


    /**
     * 修改角色
     */
    void update(Role role);


    /**
     * 根据用户 id 查询角色信息
     */
    List<Role> queryByUserId(@Param("id") Integer id);


    Permission select1(Integer id);

    void deletePermission(@Param("id") int id);

    void addPermission(ArrayList<NewPermission> list);
}