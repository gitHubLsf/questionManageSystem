package com.five.questionSystem.controller;

import com.five.questionSystem.common.Logs;
import com.five.questionSystem.entity.Role;
import com.five.questionSystem.service.RoleService;
import com.five.questionSystem.vo.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * 角色管理类
 */
@Controller
@RequestMapping("/role")
public class RoleController {


    /**
     * 根据id查询角色，返回角色对象
     */
    @RequestMapping("/update/{id}")
    @ResponseBody
    public ModelAndView queryRoleById(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Role role = roleService.queryRoleById(id);
        modelAndView.addObject("role", role);
        modelAndView.setViewName("role/admin/roleEdit");
        return modelAndView;
    }


    /**
     * 查询所有角色
     */
    @RequestMapping("/list")
    @ResponseBody
    public RespInfo queryPage(RoleReq req) {
        return roleService.queryPage(req);
    }


    /**
     * 修改角色信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "修改角色信息", operationType = "edit_role")
    public RespInfo update(RoleUpdateReq req) {
        roleService.update(req);
        respInfo.setCode(0);
        return respInfo;
    }


    @Autowired
    private RoleService roleService;

    @Autowired
    private RespInfo respInfo;

}