package com.five.questionSystem.controller;

import com.five.questionSystem.common.Logs;
import com.five.questionSystem.common.MD5Hash;
import com.five.questionSystem.common.UserDisableException;
import com.five.questionSystem.entity.*;
import com.five.questionSystem.service.RoleService;
import com.five.questionSystem.service.UserService;
import com.five.questionSystem.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;


/**
 * 用户管理类
 */
@RequestMapping("/user")
@Controller
public class UserController {

    /**
     * 用户登入
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "用户登入", operationType = "user_login")
    public RespInfo login(UserLogin user,
                          BindingResult result,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        user.validation();
        try {
            // 获取登录信息
            Subject subject = SecurityUtils.getSubject();

            // 已登陆则直接跳转到首页
            if (subject.isAuthenticated()) {
                respInfo.setCode(0);
                respInfo.setData("/view/index.jsp");
                return respInfo;
            }

            if (result.hasErrors()) {
                respInfo.setCode(-1);
                respInfo.setData("参数有误");
                return respInfo;
            }

            if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
                respInfo.setCode(-1);
                respInfo.setData("参数不能为空");
                return respInfo;
            }

            if (StringUtils.isEmpty(user.getVerCode())) {
                respInfo.setCode(-1);
                respInfo.setData("验证码不能为空");
                return respInfo;
            }

            // 校验验证码
            String trueCode = request.getSession().getAttribute("CHECKCODE_SERVER").toString();
            if (!user.getVerCode().equalsIgnoreCase(trueCode)) {
                respInfo.setCode(-1);
                respInfo.setData("验证码错误");
                return respInfo;
            }

            // 进行身份验证
            subject.login(new UsernamePasswordToken(user.getUserName(), user.getPassword()));

            // 身份验证成功,重置限制次数
            request.getSession().setAttribute("ERROR_COUNT", ERROR_MAX);

            // 判断用户是否选择记住密码
            String remember = user.getRemember();
            if (!StringUtils.isEmpty(remember) && "on".equals(remember)) {
                //往cookie中写入密码
                Cookie cookie = new Cookie(user.getUserName(), user.getPassword());
                cookie.setMaxAge(10000);
                response.addCookie(cookie);
            }

            // 验证成功在Session中保存用户信息和角色信息
            final User authUserInfo = userService.queryByName(user.getUserName());
            List<Role> roleList = roleService.queryByUserId(authUserInfo.getId());
            request.getSession().setAttribute("userInfo", authUserInfo);
            request.getSession().setAttribute("roleList", roleList);

            respInfo.setCode(0);
            respInfo.setData("/view/index.jsp");

            return respInfo;
        } catch (UserDisableException e) {
            throw new UserDisableException("账号/IP被锁定");
        } catch (AuthenticationException e) {
            Integer errorCount = (Integer) request.getSession().getAttribute("ERROR_COUNT");

            if (Objects.isNull(errorCount)) {
                request.getSession().setAttribute("ERROR_COUNT", 0);
            }

            errorCount = (Integer) request.getSession().getAttribute("ERROR_COUNT");

            if (errorCount <= 0) {
                //禁用账号
                userService.disableUser(user.getUserName());
                throw new AuthenticationException("账号/IP被锁定,请30分钟后重试");
            } else {
                request.getSession().setAttribute("ACCOUNT_ERROR_COUNT", errorCount - 1);

                //判断是用户名错误还是密码错误
                User userExit = userService.queryByName(user.getUserName());
                if (Objects.isNull(userExit)) {
                    // 用户不存在
                    throw new AuthenticationException("用户名错误,还有" + (ERROR_MAX - 1) + "次机会");
                } else {
                    // 用户存在,但是密码错误
                    throw new AuthenticationException("密码错误,还有" + (ERROR_MAX - 1) + "次机会");
                }
            }
        }
    }


    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    @ResponseBody
    @Logs(operationName = "用户登出", operationType = "user_logout")
    public RespInfo logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 自动填充密码
     */
    @RequestMapping("/getPassword")
    @ResponseBody
    public RespInfo getPassword(String name, HttpServletRequest request) {
        respInfo.setCode(-1);
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll("\\s*", "");
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    respInfo.setCode(0);
                    respInfo.setData(cookie.getValue());
                    break;
                }
            }
        }
        return respInfo;
    }


    /**
     * 校验当前用户的密码
     */
    @RequestMapping("/check")
    @ResponseBody
    public RespInfo check(@Param("pwd") String pwd, HttpServletRequest req) {
        respInfo.setCode(-1);

        //从session中获取用户信息，判断密码是否正确
        User userInfo = (User) req.getSession().getAttribute("userInfo");
        if (userInfo.getPassword().equals(MD5Hash.md5(pwd))) {
            respInfo.setCode(0);
        }

        return respInfo;
    }


    /**
     * 根据id查询用户，返回用户对象
     */
    @RequestMapping("{id}")
    @ResponseBody
    public ModelAndView queryById(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.queryById(id);
        //查询用户的老师
        User teacher = userService.queryUserTeacher(id);
        if (teacher != null) {
            user.setTeacherName(teacher.getUserName());
        }
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/admin/userDetail");
        return modelAndView;
    }


    /**
     * 根据id查询用户，返回用户对象
     */
    @RequestMapping("/update/{id}")
    @ResponseBody
    public ModelAndView queryById1(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.queryById(id);
        //查询用户的老师
        User teacher = userService.queryUserTeacher(id);
        if (teacher != null) {
            user.setTeacherName(teacher.getUserName());
        }
        //查询系统中的所有老师
        List<User> teacherList = userService.getAllTeacher();
        user.setTeacherList(teacherList);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/admin/userEdit");
        return modelAndView;
    }


    /**
     * 显示所有用户
     */
    @RequestMapping("/list")
    @ResponseBody
    @Logs(operationName = "查询用户", operationType = "22")
    public RespInfo queryPage(UserReq req) {
        return userService.queryPage(req);
    }


    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Logs(operationName = "删除用户", operationType = "delete_user")
    public RespInfo delete(Integer id) {
        this.userService.deleteById(id);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 批量删除用户
     */
    @RequestMapping("/batchDelete")
    @ResponseBody
    @Logs(operationName = "批量删除用户", operationType = "batchDelete_user")
    public RespInfo batchDelete(Integer[] ids) {
        userService.deleteByIds(ids);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 管理员修改用户信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "修改用户信息", operationType = "update_user")
    public RespInfo update(UserUpdateReq req) {
        userService.update(req);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户修改自己的资料
     */
    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "用户修改个人信息", operationType = "update_person_info")
    public RespInfo updatePersonInfo(UserUpdateReq req) {
        userService.updatePersonInfo(req);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户修改自己的密码
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "用户修改密码", operationType = "update_person_password")
    public RespInfo updatePassword(UpdatePassword req, HttpServletRequest request, HttpServletResponse response) {
        //判断旧密码是否正确
        User user = (User) request.getSession().getAttribute("userInfo");
        String password = user.getPassword();
        if (!password.equals(MD5Hash.md5(req.getOldPassword()))) {
            respInfo.setCode(-1);
            return respInfo;
        }

        ///旧密码正确，更新用户信息
        UserUpdateReq userUpdateReq = new UserUpdateReq();
        userUpdateReq.setId(user.getId());
        userUpdateReq.setPassword(req.getNewPassword());
        this.userService.updatePersonInfo(userUpdateReq);

        //更新cookie
        Cookie cookie = new Cookie(user.getUserName(), req.getNewPassword());
        cookie.setMaxAge(100000);
        response.addCookie(cookie);

        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 添加用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "添加用户", operationType = "add_user")
    public RespInfo add(UserAddReq req) {
        userService.insert(req);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 用户注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "用户注册", operationType = "user_register")
    public RespInfo register(UserAddReq req) {
        userService.insert(req);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 查看用户名是否存在
     */
    @RequestMapping(value = "/userExit")
    @ResponseBody
    public RespInfo userExit(String name) {
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll("\\s*", "");
        }
        //查看用户名是否存在
        int flag = (userService.queryByName(name) != null) ? 0 : -1;
        respInfo.setCode(flag);
        return respInfo;
    }


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RespInfo respInfo;

    // 身份验证的限制次数
    private Integer ERROR_MAX = 3;
}