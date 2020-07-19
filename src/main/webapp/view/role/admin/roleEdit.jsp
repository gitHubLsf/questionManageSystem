<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改角色</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
    <script src="/view/js/jquery-3.3.1.js"></script>
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">

    <div class="layui-form-item">
        <input type="hidden" name="id" value="${role.id}">
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 150px">角色</label>
        <div class="layui-input-inline">
            <input type="text" name="roleName" autocomplete="off" class="layui-input" value="${role.roleName}"
                   readonly="readonly" style="width: 350px" id="roleName">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 150px">具体描述</label>
        <div class="layui-input-inline">
            <textarea name="roleDesc" class="layui-textarea" id="ques"
                      style="width: 500px; margin-left: 0px; height: 200px">${role.roleDesc}</textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 150px">权限范围</label>
        <div class="layui-input-inline" style="width: 1000px;">
            <input type="checkbox" title="查看错题" value="1" id="ps1">
            <input type="checkbox" title="删除错题" value="2" id="ps2">
            <input type="checkbox" title="修改错题" value="3" id="ps3">
            <input type="checkbox" title="从文档导入错题" value="4" id="ps4">
            <input type="checkbox" title="OCR文字识别错题" value="5" id="ps5">
            <input type="checkbox" title="手动输入错题" value="6" id="ps6">
            <input type="checkbox" title="导入我的题库" value="8" id="ps8">
            <hr>
            <input type="checkbox" title="查看练习" value="14" id="ps14">
            <input type="checkbox" title="删除练习" value="12" id="ps12">
            <input type="checkbox" title="添加练习" value="11" id="ps11">
            <input type="checkbox" title="修改练习" value="15" id="ps15">
            <input type="checkbox" title="抽题组建练习" value="7" id="ps7">
            <input type="checkbox" title="下载练习" value="13" id="ps13">
            <hr>
            <input type="checkbox" title="查看日志" value="9" id="ps9">
            <input type="checkbox" title="删除日志" value="10" id="ps10">
            <hr>
            <input type="checkbox" title="查看用户" value="22" id="ps22">
            <input type="checkbox" title="添加用户" value="21" id="ps21">
            <input type="checkbox" title="修改用户" value="24" id="ps24">
            <input type="checkbox" title="删除用户" value="23" id="ps23">
            <hr>
            <input type="checkbox" title="查看角色" value="19" id="ps19">
            <input type="checkbox" title="删除角色" value="20" id="ps20">
        </div>
    </div>

    <script>
        <c:forEach items="${role.ps }" var="aa" >
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

        //提交
        form.on('submit(LAY-user-front-submit)', function (obj) {
            var field = obj.field;

            if ($.trim(field.roleDesc) === "") {
                layer.msg('请输入具体描述信息', {
                    offset: '15px'
                    , icon: 1
                    , time: 2000
                });
                return false;
            }

            var listVal = [];
            $("input[type=checkbox]:checked").each(function () {
                listVal.push(parseInt($(this).val()));
            });

            $.ajax({
                type: 'post',
                url: "/role/update",
                data: {
                    "id": field.id,
                    "roleName": field.roleName,
                    "roleDes": field.roleDes,
                    "ps": listVal
                },
                traditional: true,
                success: function (res) {
                    if (res.code === 0) {
                        layer.open({
                            title: "提示"
                            , content: "修改成功"
                        });
                        //修改成功,跳转到角色列表
                        window.location.href = '/view/role/admin/roleList.jsp';
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
    })
</script>
</body>
</html>