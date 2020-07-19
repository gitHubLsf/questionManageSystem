<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统练习库</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item layui-inline">

                <div class="layui-inline" style="width: 410px">
                    <label class="layui-form-label">练习名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input"
                               style="width: 300px;">
                    </div>
                </div>

                <div class="layui-inline" style="width: 410px">
                    <label class="layui-form-label">创建人员</label>
                    <div class="layui-input-block">
                        <input type="text" name="userName" placeholder="请输入" autocomplete="off" class="layui-input"
                               style="width: 300px;">
                    </div>
                </div>

                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-user-back-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="layui-card-body">
            <div style="padding-bottom: 10px;">
                <shiro:hasPermission name="delete_practice">
                    <button class="layui-btn layui-btn-danger layui-btn-radius" data-type="batchDelete">批量删除</button>
                </shiro:hasPermission>
            </div>

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <script type="text/html" id="table-useradmin-admin">
                <shiro:hasPermission name="delete_practice">
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i
                            class="layui-icon layui-icon-delete"></i>删除</a>
                </shiro:hasPermission>
            </script>
        </div>
    </div>

</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="date1">
    {{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}
</script>
<script type="text/html" id="tem1">
    {{#  layui.each(d.parts, function(index, item){ }}
    ({{ item.questionCount  }}
    {{# if (item.type == 0) { }}
    道:选择题
    {{#  } else if (item.type == 1) {  }}
    道:填空题
    {{#  } else if (item.type == 2) {  }}
    道:问答题
    {{#  }   }} |
    {{# if (item.difficulty == 0) { }}
    难度:简单
    {{#  } else if (item.difficulty == 1) {  }}
    难度:较难
    {{#  } else if (item.difficulty == 2) {  }}
    难度:很难
    {{#  }   }} |
    {{# if (item.grade == 0) { }}
    适合:一年级
    {{# } else if (item.grade == 1) { }}
    适合:二年级
    {{# } else if (item.grade == 2) { }}
    适合:三年级
    {{# } else if (item.grade == 3) { }}
    适合:四年级
    {{# } else if (item.grade == 4) { }}
    适合:五年级
    {{# } else if (item.grade == 5) { }}
    适合:六年级
    {{# } else if (item.grade == 6) { }}
    适合:初一
    {{# } else if (item.grade == 7) { }}
    适合:初二
    {{# } else if (item.grade == 8) { }}
    适合:初三
    {{# } }})
    {{#  }); }}
</script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'table', 'admin'], function () {
        var $ = layui.$
            , form = layui.form
            , table = layui.table;

        table.render({
            elem: '#LAY-user-back-manage'
            , url: '/practice/admin/list'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: '学号', sort: true}
                , {field: 'name', width: 250, title: '练习名称'}
                , {field: 'parts', width: 450, title: '详细信息', templet: "#tem1"}
                , {field: 'userName', width: 250, title: '创建人员'}
                ,{
                    field: 'create_time',
                    title: '修改时间',
                    templet: '#date1',
                    minWidth: 60,
                    align: 'center',
                    sort: true
                }
                , {title: '操作', width: 230, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
            ]]
            , page: true
            , limit: 10
            , height: '500'
            , text: '对不起，加载出现异常！'
        });

        form.on('submit(LAY-user-back-search)', function (data) {
            table.reload('LAY-user-back-manage', {
                where: data.field
            });
        });

        table.on('tool(LAY-user-back-manage)', function (obj) {
            var data = obj.data;
            if (obj.event === 'delete') {  // 删除试卷
                layer.prompt({
                    formType: 1
                    , title: '请输入密码'
                }, function (value, index) {
                    $.ajax({
                        url: "/user/check",
                        data: {"pwd": value},
                        success: function (res) {
                            layer.close(index);
                            if (res.code === 0) {
                                layer.confirm('确定删除这份练习吗？', function (index) {
                                    $.ajax({
                                        url: "/practice/delete",
                                        data: {"id": data.id},
                                        success: function (res) {
                                            layer.close(index);
                                            if (res.code === 0) {
                                                layer.open({
                                                    title: "提示"
                                                    , content: "删除成功"
                                                });
                                            } else {
                                                layer.open({
                                                    title: "提示"
                                                    , content: '删除失败:' + res.msg
                                                });
                                            }
                                        },
                                        error: function () {
                                            layer.close(index);
                                            location.href = '/view/tips/error.html';
                                        }
                                    });
                                    table.reload('LAY-user-back-manage', {
                                        page: {
                                            curr: 1
                                        }
                                    });
                                });
                            } else {
                                layer.open({
                                    title: "提示"
                                    , content: "密码错误，不允许"
                                });
                            }
                        },
                        error: function () {
                            layer.close(index);
                            location.href = '/view/tips/error.html';
                        }
                    });
                });
            }
        });

        var active = {
            batchDelete: function () { //批量删除练习
                var checkStatus = table.checkStatus('LAY-user-back-manage')
                    , checkData = checkStatus.data;

                if (checkData.length === 0) {
                    return layer.msg('请选择数据');
                }

                var idArr = [];
                for (var i = 0; i < checkData.length; i++) {
                    idArr[i] = checkData[i].id;
                }

                layer.prompt({
                    formType: 1
                    , title: '请输入密码'
                }, function (value, index) {
                    $.ajax({
                        url: "/user/check",
                        data: {"pwd": value},
                        success: function (res) {
                            layer.close(index);
                            if (res.code === 0) {
                                layer.confirm('确定删除这些练习吗？', function (index) {
                                    $.ajax({
                                        url: "/practice/batchDelete",
                                        data: {"ids": idArr},
                                        traditional: true
                                    });
                                    layer.close(index);
                                    table.reload('LAY-user-back-manage', {
                                        page: {
                                            curr: 1
                                        }
                                    });
                                });
                            } else {
                                layer.open({
                                    title: "提示"
                                    , content: "密码错误，不允许"
                                });
                            }
                        },
                        error:function () {
                            layer.close(index);
                            location.href = '/view/tips/error.html';
                        }
                    });
                });
            }
        };

        $('.layui-btn.layui-btn-radius').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>


