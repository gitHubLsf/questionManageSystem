<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">

    <div class="layui-form-item">
        <label class="layui-form-label">登录名</label>
        <div class="layui-input-inline">
            <button class="layui-btn  layui-btn-lg layui-btn-radius">${user.userName}</button>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-inline">
            <c:if test="${user.sex == 0}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius">男</button>
            </c:if>

            <c:if test="${user.sex == 1}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius">女</button>
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-inline">
            <button class="layui-btn  layui-btn-lg layui-btn-radius">${user.phone}</button>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-inline">
            <button class="layui-btn  layui-btn-lg layui-btn-radius">${user.email}</button>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-inline">
            <c:if test="${user.status == 0}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius layui-btn-danger">禁用</button>
            </c:if>

            <c:if test="${user.status == 1}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius ">启用</button>
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">角色</label>
        <div class="layui-input-inline">
            <button class="layui-btn layui-btn-lg layui-btn-radius">
                <c:forEach items="${user.roleList }" var="aa">
                    ${aa.roleName}&nbsp;&nbsp;
                </c:forEach>
            </button>
        </div>
    </div>

    <c:if test="${user.roleList.get(0).id == 3}">
        <div class="layui-form-item">
            <label class="layui-form-label">所属的老师</label>
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-lg layui-btn-radius">
                        ${user.teacherName}
                </button>
            </div>
        </div>
    </c:if>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'form']);
</script>
</body>
</html>