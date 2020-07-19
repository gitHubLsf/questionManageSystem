<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>手动输入错题</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">

    <div class="layui-form-item">
        <label class="layui-form-label">题目</label>
        <textarea name="name" class="layui-textarea" id="ques"
                  style="width: 500px; margin-left: 100px; height: 200px"></textarea>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">类型</label>
        <div class="layui-input-inline" style="width: 400px">
            <input type="radio" name="type" value="1" title="填空题" checked>
            <input type="radio" name="type" value="2" title="问答题">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label ">难度</label>
        <div class="layui-input-inline" style="width: 400px">
            <input type="radio" name="difficulty" value="0" title="简单" checked>
            <input type="radio" name="difficulty" value="1" title="较难">
            <input type="radio" name="difficulty" value="2" title="很难">
        </div>
    </div>

    <shiro:hasAnyRoles name="teacher, admin">
        <div class="layui-form-item">
            <label class="layui-form-label">适用年级</label>
            <div class="layui-input-inline" style="width: 220px;">
                <select name="grade" style="width: 220px;">
                    <option value="0">一年级</option>
                    <option value="1">二年级</option>
                    <option value="2">三年级</option>
                    <option value="3">四年级</option>
                    <option value="4" selected>五年级</option>
                    <option value="5">六年级</option>
                    <option value="6">初一</option>
                    <option value="7">初二</option>
                    <option value="8">初三</option>
                </select>
            </div>
        </div>
    </shiro:hasAnyRoles>

    <div class="layui-form-item layui-hide">
        <button class="layui-btn" lay-submit lay-filter="LAY-user-front-submit" id="LAY-user-back-submit"></button>
    </div>
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