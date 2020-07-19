<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>日志详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">

    <div class="layui-form-item">
        <label class="layui-form-label">日志类型</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.logType}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">操作人员</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.createBy}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">访问时间</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.visitTime}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">IP地址</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.ip}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.description}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">方法名</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.method}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">参数</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.params}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">错误代码</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.exceptionCode}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">错误信息</label>
        <div class="layui-input-inline">
            <input type="text" autocomplete="off" class="layui-input" value="${log.exceptionDetail}"
                   readonly="readonly" style="width: 400px">
        </div>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'form'], function () {
    })
</script>
</body>
</html>