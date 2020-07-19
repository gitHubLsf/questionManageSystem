package com.five.questionSystem.common;


import com.five.questionSystem.entity.Permission;
import com.five.questionSystem.entity.Role;
import com.five.questionSystem.entity.User;
import com.five.questionSystem.service.RoleService;
import com.five.questionSystem.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Realm，Security Manager 进行身份验证，角色检查，权限检查时，都需要通过 Realm 到数据库中查询这些信息
 * 必要时，可以将这些信息通过 CacheManager 缓存起来，不必每次都查数据库，提高执行效率
 * 且 Realm 可以使用系统自带的，也可以自定义，一般都是自定义实现
 */
public class SecurityRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 登录时进行身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // token:登录令牌
        // 获取用户名
        String username = String.valueOf(token.getPrincipal());

        //获取密码(明文)
        //String password = new String((char[]) token.getCredentials());

        //到数据库中验证用户是否存在
        final User authentication = userService.UserLogin(username);

        if (authentication == null) {
            throw new AuthenticationException("用户不存在");
        }

        //判断账号是否禁用
        if (authentication.getStatus() == 0) {
            throw new UserDisableException("账号被禁用");
        }

        //开始验证密码
        //因为数据库中的密码是加密存储的，所以此处需要将明文转为暗文后，再进行验证
        //getName指的是当前realm的名称
        //siggy是盐，对明文加密时需要盐
        ByteSource siggy = ByteSource.Util.bytes("siggy");

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, authentication.getPassword(), siggy, this.getName());

        return authenticationInfo;
    }


    /**
     * 身份验证成功后，查询用户拥有的所有角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws BusinessException {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //principals：身份，即主体的标识属性，
        //一个主体可以有多个属性（用户名，手机号，邮箱等，但只有一个Primary principals，这里指的是用户名
        String username = String.valueOf(principals.getPrimaryPrincipal());

        //通过获取的username查询数据库中是否有对应的用户名
        User user = null;
        try {
            user = userService.queryByName(username);
        } catch (Exception e) {
            throw new BusinessException("数据库访问异常");
        }

        if (user == null) {
            return null;
        }

        //如果用户名不为空，则通过用户id查询出用户角色
        List<Role> roleInfos = null;
        try {
            roleInfos = roleService.queryByUserId(user.getId());
        } catch (Exception e) {
            throw new BusinessException("数据库访问异常");
        }

        for (Role role : roleInfos) {
            //添加角色
            authorizationInfo.addRole(role.getRoleCode());

            //获取角色拥有的权限
            List<Permission> ps = role.getPs();
            for (Permission per : ps) {
                authorizationInfo.addStringPermission(per.getPermissionName());
            }
        }

        //返回用户拥有的角色和权限
        return authorizationInfo;
    }
}