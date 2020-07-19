package com.five.questionSystem.service.impl;

import com.five.questionSystem.entity.Role;
import com.five.questionSystem.dao.RoleDao;
import com.five.questionSystem.service.RoleService;
import com.five.questionSystem.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public Role queryRoleById(Integer id) {
        return roleDao.queryRoleById(id);
    }


    @Override
    public void update(RoleUpdateReq req) {
        req.volidation();

        Role role = new Role();
        BeanUtils.copyProperties(req, role);
        this.roleDao.update(role);

        //查看是否需要修改权限
        if (req.getPs() != null && req.getPs().length != 0) {
            //删除旧的权限
            roleDao.deletePermission(req.getId());

            //增加新的权限
            Integer[] psArr = req.getPs();
            ArrayList<NewPermission> list = new ArrayList<>();
            for (int i = 0; i < psArr.length; i++) {
                list.add(new NewPermission(req.getId(), psArr[i]));
            }
            roleDao.addPermission(list);
        }
    }


    @Override
    public RespInfo queryPage(RoleReq req) {
        req.valiation();

        Role role = new Role();
        BeanUtils.copyProperties(req, role);

        PageHelper.startPage(req.getPage(), req.getLimit());
        List<Role> list = roleDao.queryAll(role);

        PageInfo<Role> pageInfo = new PageInfo<>(list);

        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }

    @Override
    public List<Role> queryByUserId(Integer id) {
        return roleDao.queryByUserId(id);
    }


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RespInfo respInfo;
}