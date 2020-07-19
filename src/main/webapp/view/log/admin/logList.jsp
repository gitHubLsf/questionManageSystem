<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>日志管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item layui-inline">

                <div class="layui-inline" style="width: 450px">
                    <label class="layui-form-label">操作人员</label>
                    <div class="layui-input-block">
                        <input type="text" name="createBy" placeholder="请输入" autocomplete="off" class="layui-input">
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
                <button class="layui-btn layui-btn-danger layui-btn-radius" data-type="batchDel">批量删除</button>
            </div>

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <script type="text/html" id="table-useradmin-admin">
                <shiro:hasPermission name="cat_log">
                    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i
                            class="layui-icon layui-icon-edit"></i>详情</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="delete_log">
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i
                            class="layui-icon layui-icon-delete"></i>删除</a>
                </shiro:hasPermission>
            </script>
        </div>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="date1">
    {{layui.util.toDateString(d.visitTime, 'yyyy-MM-dd HH:mm:ss')}}
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
            , url: '/log/list'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: '序号', sort: true, unresize: true}
                , {field: 'createBy', width: 150, title: '操作人员'}
                , {field: 'visitTime', width: 230, title: '访问时间', align: 'center', templet: '#date1'}
                , {field: 'ip', title: 'ip地址', width: 100, align: 'center'}
                , {
                    field: 'description',
                    title: '描述',
                    minWidth: 60,
                    align: 'center',
                    event: 'des',
                    style: 'cursor: pointer;'
                }
                , {
                    title: '操作',
                    width: 230,
                    align: 'center',
                    fixed: 'right',
                    toolbar: '#table-useradmin-admin',
                    unresize: true
                }
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
            if (obj.event === 'delete') {  // 删除日志
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
                                layer.confirm('确定删除这条日志吗？', function (index) {
                                    $.ajax({
                                        url: "/log/del",
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
                                                    , content: "删除失败:" + res.msg
                                                });
                                            }
                                        }, error: function () {
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
                                    , content: "密码错误,不允许删除"
                                });
                            }
                        },
                        error: function () {
                            layer.close(index);
                            location.href = '/view/tips/error.html';
                        }
                    });
                });
            } else if (obj.event === 'detail') {  // 查看日志详情
                layer.open({
                    type: 2
                    , title: '日志详情'
                    , maxmin: true
                    , content: '/log/' + obj.data.id
                    , area: ['600px', '650px']
                })
            } else if (obj.event === 'des') {
                layer.open({
                    formType: 2
                    , maxmin: true
                    , title: '查看日志序号为 [' + obj.data.id + '] 的详细内容'
                    , content: obj.data.description
                    , area: ['400px', '300px']
                });
            }
        });


        var active = {
            batchDel: function () { //批量删除日志
                var checkStatus = table.checkStatus('LAY-user-back-manage')
                    , checkData = checkStatus.data;

                if (checkData.length === 0) {
                    return layer.msg('请选择数据');
                }

                let idArr = [];
                for (let i = 0; i < checkData.length; i++) {
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
                                layer.confirm('确定删除这些日志吗？', function (index) {
                                    $.ajax({
                                        url: "/log/batchDelete",
                                        data: {"ids": idArr},
                                        traditional: true,
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
                                                    , content: "删除失败:" + res.msg
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
        };

        $('.layui-btn.layui-btn-radius').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>


