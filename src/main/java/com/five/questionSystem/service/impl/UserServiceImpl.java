package com.five.questionSystem.service.impl;


import com.five.questionSystem.common.DBUtils;
import com.five.questionSystem.common.MD5Hash;
import com.five.questionSystem.dao.RoleDao;
import com.five.questionSystem.dao.UserDao;
import com.five.questionSystem.entity.Question;
import com.five.questionSystem.entity.Role;
import com.five.questionSystem.entity.User;
import com.five.questionSystem.service.UserService;
import com.five.questionSystem.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.nio.cs.US_ASCII;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {


    @Override
    public User queryById(Integer id) {
        return userDao.queryById(id);
    }


    @Override
    public void insert(UserAddReq req) {
        req.volidation();

        User user = new User();
        BeanUtils.copyProperties(req, user);
        user.setId(db.getKey("USER_ID"));
        //密码加密
        user.setPassword(MD5Hash.md5(user.getPassword()));

        if (Objects.isNull(user.getRole())) {
            //如果没有分配角色，则默认为学生
            user.setRole(3);
        }

        //账号启用
        user.setStatus(1);
        this.userDao.insert(user);
    }


    @Override
    public void update(UserUpdateReq req) {
        req.volidation();

        //查看用户是否需要修改密码
        String password = req.getPassword();
        if (!StringUtil.isEmpty(password)) {
            //要修改密码，密码加密
            req.setPassword(MD5Hash.md5(password));
        }

        User user = new User();
        BeanUtils.copyProperties(req, user);
        this.userDao.update(user);

        //查看用户是否为学生
        if (req.getTeacherId() != null) {
            //查看该学生是否有老师
            User teacher = userDao.queryUserTeacher(req.getId());
            if (Objects.isNull(teacher)) {
                userDao.insertUserTeacher(req);
            } else {
                userDao.updateUserTeacher(req);
            }
        }
    }


    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }


    @Override
    public User queryByName(String userName) {
        return this.userDao.queryByName(userName);
    }


    @Override
    public RespInfo queryPage(UserReq req) {
        req.valiation();

        User user = new User();
        BeanUtils.copyProperties(req, user);

        //开启分页
        PageHelper.startPage(req.getPage(), req.getLimit());
        List<User> list = userDao.queryAll(user);

        PageInfo<User> pageInfo = new PageInfo<>(list);

        //封装响应体
        respInfo.setCode(0);
        respInfo.setMsg("true");
        respInfo.setCount(pageInfo.getTotal());
        respInfo.setData(pageInfo.getList());

        return respInfo;
    }


    @Override
    public void deleteByIds(Integer[] ids) {
        userDao.deleteByIds(ids);
    }


    @Override
    public List<User> queryAll(User user) {
        return userDao.queryAll(user);
    }


    @Override
    public void updatePersonInfo(UserUpdateReq req) {
        req.volidation();

        //查看用户是否需要修改密码
        String password = req.getPassword();
        if (!StringUtil.isEmpty(password)) {
            //要修改密码，密码加密
            req.setPassword(MD5Hash.md5(password));
        }

        User user = new User();
        BeanUtils.copyProperties(req, user);
        this.userDao.update(user);
    }


    @Override
    public User UserLogin(String userName) {
        return userDao.queryByName(userName);
    }


    @Override
    public void disableUser(String userName) {
        userDao.disableUser(userName);
    }


    @Override
    public User queryUserTeacher(Integer id) {
        return userDao.queryUserTeacher(id);
    }


    @Override
    public List<User> getAllTeacher() {
        return userDao.getAllTeacher();
    }


    @Autowired
    private UserDao userDao;


    @Autowired
    private DBUtils db;

    @Autowired
    private RespInfo respInfo;
}