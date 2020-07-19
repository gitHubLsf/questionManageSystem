<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改我的资料</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">修改个人信息</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">

                        <div class="layui-form-item layui-hide">
                            <input type="hidden" name="id" value="${sessionScope.userInfo.id}">
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">我的角色</label>
                            <div class="layui-input-inline" style="width: 250px;">
                                <select>
                                    <c:forEach items="${sessionScope.roleList }" var="aa">
                                        <option selected>${aa.roleName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-form-mid layui-word-aux">当前角色不可更改为其它角色</div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline" style="width: 250px;">
                                <input type="text" name="userName" lay-verify="required"
                                       value="${sessionScope.userInfo.userName}"
                                       class="layui-input" style="width: 250px;">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">手机</label>
                            <div class="layui-input-inline" style="width: 250px;">
                                <input type="text" name="phone" value="${sessionScope.userInfo.phone}"
                                       lay-verify="phone" autocomplete="off"
                                       class="layui-input" style="width: 250px;">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">邮箱</label>
                            <div class="layui-input-inline" style="width: 250px;">
                                <input type="text" name="email" value="${sessionScope.userInfo.email}"
                                       lay-verify="email" autocomplete="off"
                                       class="layui-input" style="width: 250px;">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmyinfo">确认修改</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script>

</script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'set'], function () {
        var $ = layui.$
            , form = layui.form;

        form.on('submit(setmyinfo)', function (data) {
            var field = data.field;

            $.ajax({
                type: "post",
                url: "/user/updatePerson",
                data: {
                    "id": field.id,
                    "userName": field.userName,
                    "phone": field.phone,
                    "email": field.email
                },
                success: function (res) {
                    if (res.code === 0) {
                        layer.msg('修改成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 2500
                        }, function () {
                            //修改成功后，用户需要重新登录
                            //先登出
                            $.ajax({
                                url: "/user/logout",
                                success: function (res) {
                                    if (res.code === 0) {
                                        window.parent.location.href = 'login.html';
                                    } else {
                                        layer.open({
                                            title: "提示"
                                            , content: "请重新登录"
                                        });
                                    }
                                },
                                error: function () {
                                    location.href = '/view/tips/error.html';
                                }
                            });
                        });
                    } else {
                        layer.open({
                            title: "提示"
                            , content: "修改失败:" + res.msg
                        });
                    }
                },
                error: function () {
                    location.href = '/view/tips/error.html';
                }
            });
        });
    });
</script>
</body>
</html>