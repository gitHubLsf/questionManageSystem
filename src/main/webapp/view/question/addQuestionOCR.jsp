<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>OCR识别错题</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">

    <div class="layui-form-item" style="margin-left: -20px;">
        <label class="layui-form-label" style="width: 100px">图片</label>
        <div class="layui-input-inline">
            <div class="layui-upload">
                <div class="layui-upload-list">
                    <img class="layui-upload-img" id="test-upload-normal-img" style="width: 450px; height: 145px">
                </div>
                <button type="button" class="layui-btn" id="test-upload-normal" style="width: 120px">上传图片</button>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">题目</label>
        <textarea name="name" class="layui-textarea" id="ques"
                  style="width: 450px; margin-left: 110px; height: 140px"></textarea>
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
            <div class="layui-input-inline" style="width: 230px">
                <select name="grade" style="width: 230px;">
                    <option value="0" >一年级</option>
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
    }).use(['index', 'form', 'upload'], function () {
        var $ = layui.$
            , upload = layui.upload;

        //普通图片上传
        upload.render({
            elem: '#test-upload-normal'
            , url: '/question/imgImport'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#test-upload-normal-img').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code === 0) {
                    $("#ques").val(res.msg);
                    return layer.msg('上传成功');
                }
                return layer.msg('上传失败');
            }, error: function () {
                return layer.msg('服务器异常,稍后再试');
            }
        });
    })
</script>
</body>
</html>