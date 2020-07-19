<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改错题</title>
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
                <button class="layui-btn layui-btn-lg layui-btn-radius">选择题</button>
            </c:if>

            <c:if test="${ques.type == 1}">
                <button class="layui-btn layui-btn-lg layui-btn-radius">填空题</button>
            </c:if>

            <c:if test="${ques.type == 2}">
                <button class="layui-btn layui-btn-lg layui-btn-radius">问答题</button>
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">难度</label>
        <div class="layui-input-inline" style="width: 400px">
            <c:if test="${ques.difficulty == 0}">
                <input type="radio" name="difficulty" value="0" title="简单" checked>
                <input type="radio" name="difficulty" value="1" title="较难">
                <input type="radio" name="difficulty" value="2" title="很难">
            </c:if>

            <c:if test="${ques.difficulty == 1}">
                <input type="radio" name="difficulty" value="0" title="简单">
                <input type="radio" name="difficulty" value="1" title="较难" checked>
                <input type="radio" name="difficulty" value="2" title="很难">
            </c:if>

            <c:if test="${ques.difficulty == 2}">
                <input type="radio" name="difficulty" value="0" title="简单">
                <input type="radio" name="difficulty" value="1" title="较难">
                <input type="radio" name="difficulty" value="2" title="很难" checked>
            </c:if>
        </div>
    </div>

    <shiro:hasAnyRoles name="teacher, admin">
        <div class="layui-form-item">
            <label class="layui-form-label">适用年级</label>
            <div class="layui-input-inline" style="width: 230px;">
                <c:if test="${ques.grade == 0}">
                    <select name="grade" style="width: 230px;">
                        <option value="0" selected>一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 1}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1" selected>二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 2}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2" selected>三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 3}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3" selected>四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 4}">
                    <select name="grade">
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
                </c:if>

                <c:if test="${ques.grade == 5}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5" selected>六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 6}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6" selected>初一</option>
                        <option value="7">初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 7}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7" selected>初二</option>
                        <option value="8">初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade == 8}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8" selected>初三</option>
                    </select>
                </c:if>

                <c:if test="${ques.grade != 0 && ques.grade != 1 && ques.grade != 2 && ques.grade != 3 && ques.grade != 4 && ques.grade != 5 && ques.grade != 6 && ques.grade != 7 && ques.grade != 8}">
                    <select name="grade">
                        <option value="0">一年级</option>
                        <option value="1">二年级</option>
                        <option value="2">三年级</option>
                        <option value="3">四年级</option>
                        <option value="4">五年级</option>
                        <option value="5">六年级</option>
                        <option value="6">初一</option>
                        <option value="7">初二</option>
                        <option value="8" selected>初三</option>
                    </select>
                </c:if>

            </div>
        </div>
    </shiro:hasAnyRoles>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="LAY-user-front-submit" id="LAY-user-back-submit" value="确认">
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