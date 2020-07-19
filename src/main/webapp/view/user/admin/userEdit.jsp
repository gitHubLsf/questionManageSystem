<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改用户信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">

    <div class="layui-form-item">
        <input type="hidden" name="id" value="${user.id}">
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline" style="width: 250px;">
            <input type="text" name="userName" lay-verify="required" placeholder="用户名"
                   class="layui-input" value="${user.userName}" style="width: 250px;">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-inline" style="width: 250px;">
            <input type="password" name="password" autocomplete="off" class="layui-input" placeholder="不填代表保留旧密码"
                   style="width: 250px;">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机</label>
        <div class="layui-input-inline" style="width: 250px;">
            <input type="text" name="phone" autocomplete="off" lay-verify="required|phone" class="layui-input"
                   value="${user.phone}" style="width: 250px;">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-inline" style="width: 250px;">
            <input type="text" name="email" autocomplete="off" lay-verify="required|email" class="layui-input"
                   value="${user.email}" style="width: 250px;">
        </div>
    </div>

    <c:if test="${user.roleList.get(0).id == 3}">
        <div class="layui-form-item">
            <label class="layui-form-label">所属的老师</label>
            <div class="layui-input-inline">
                <select name="teacherId">
                    <c:forEach items="${user.teacherList }" var="aa">
                        <c:if test="${aa.userName == user.teacherName}">
                            <option value="${aa.id}" selected="selected">${aa.userName}</option>
                        </c:if>
                        <c:if test="${aa.userName != user.teacherName}">
                            <option value="${aa.id}">${aa.userName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
    </c:if>

    <div class="layui-form-item">
        <label class="layui-form-label">审核状态</label>
        <div class="layui-input-inline">
            <c:if test="${user.status == 0}">
                <input type="radio" name="status" value="0" title="禁用" checked>
                <input type="radio" name="status" value="1" title="启用">
            </c:if>

            <c:if test="${user.status == 1}">
                <input type="radio" name="status" value="0" title="禁用">
                <input type="radio" name="status" value="1" title="启用" checked>
            </c:if>
        </div>
    </div>

    <script>
        <c:forEach items="${user.roleList }" var="aa" >
        $("#ps${aa.id}").attr("checked", "checked");
        </c:forEach>
    </script>

    <div class="layui-form-item">
        <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-front-submit"
                style="width: 190px;margin-left: 110px;">确认修改
        </button>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'form', 'admin'], function () {
        var $ = layui.$,
            form = layui.form,
            admin = layui.admin;

        form.on('submit(LAY-user-front-submit)', function (obj) {
            var field = obj.field;

            $.ajax({
                type: 'post',
                url: "/user/update",
                data: {
                    "id": field.id,
                    "userName": field.userName,
                    "password": field.password,
                    "phone": field.phone,
                    "email": field.email,
                    "status": field.status,
                    "teacherId": field.teacherId
                },
                traditional: true,
                success: function (res) {
                    if (res.code === 0) {
                        layer.msg('修改成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 2500
                        }, function () {
                            //修改成功，跳转到用户列表
                            window.location.href = '/view/user/admin/userList.jsp';
                        });
                    } else {
                        layer.msg('修改失败:' + res.msg, {
                            offset: '15px'
                            , icon: 1
                            , time: 2500
                        });
                    }
                },
                error: function () {
                    location.href = '/view/tips/error.html';
                }
            });
        });
    })
</script>
</body>
</html>