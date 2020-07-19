<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生错题详情</title>
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
        <div class="layui-input-inline">
            <textarea name="name" class="layui-textarea" id="ques"
                      style="width: 450px; margin-left: 0px; height: 160px" disabled="disabled">${ques.name}
    <c:if test="${ques.type == 0}"><c:forEach items="${ques.ops }" var="aa">
        ${aa.opt }</c:forEach></c:if>
            </textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">类型</label>
        <div class="layui-input-inline">
            <c:if test="${ques.type == 0}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius">选择题</button>
            </c:if>

            <c:if test="${ques.type == 1}">
                <button class="layui-btn layui-btn-lg layui-btn-radius">填空题</button>
            </c:if>

            <c:if test="${ques.type == 2}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius">问答题</button>
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">难度</label>
        <div class="layui-input-inline">
            <c:if test="${ques.difficulty == 0}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius">简&nbsp;&nbsp;&nbsp;&nbsp;单</button>
            </c:if>

            <c:if test="${ques.difficulty == 1}">
                <button class="layui-btn  layui-btn-lg layui-btn-radius">较&nbsp;&nbsp;&nbsp;&nbsp;难</button>
            </c:if>

            <c:if test="${ques.difficulty == 2}">
                <button class="layui-btn  layui-btn-warm layui-btn-lg layui-btn-radius">很&nbsp;&nbsp;&nbsp;&nbsp;难
                </button>
            </c:if>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">学生姓名</label>
        <div class="layui-input-inline">
            <button class="layui-btn layui-btn-lg layui-btn-radius">${ques.userName}</button>
        </div>
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