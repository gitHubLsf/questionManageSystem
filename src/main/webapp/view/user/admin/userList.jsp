<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户列表</title>
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

                <div class="layui-inline" style="width: 350px">
                    <label class="layui-form-label">登录名</label>
                    <div class="layui-input-block">
                        <input type="text" name="userName" placeholder="请输入" autocomplete="off" class="layui-input"
                               style="width: 250px">
                    </div>
                </div>

                <div class="layui-inline" style="width: 200px">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone" placeholder="请输入" autocomplete="off" class="layui-input"
                               style="width: 150px;">
                    </div>
                </div>

                <div class="layui-inline" style="width: 230px">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" placeholder="请输入" autocomplete="off" class="layui-input"
                               style="width: 150px;">
                    </div>
                </div>

                <div class="layui-inline" style="width: 230px">
                    <label class="layui-form-label">审核状态</label>
                    <div class="layui-input-block">
                        <select name="status">
                            <option value="">全部</option>
                            <option value="0">禁用</option>
                            <option value="1">启用</option>
                        </select>
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
                <shiro:hasPermission name="delete_user">
                    <button class="layui-btn layui-btn-danger layui-btn-radius" data-type="batchDelete">批量删除</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="add_user">
                    <button class="layui-btn layui-btn-radius" data-type="manAdd">添加用户</button>
                </shiro:hasPermission>
            </div>

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <script type="text/html" id="buttonTpl">
                {{#  if(d.sex == 0){ }}
                <button class="layui-btn layui-btn-sm">男</button>
                {{#  } else if(d.sex == 1) { }}
                <button class="layui-btn layui-btn-sm">女</button>
                {{#  } else { }}
                <button class="layui-btn layui-btn-sm layui-btn-danger">异常</button>
                {{#  }  }}
            </script>

            <script type="text/html" id="buttonTp2">
                {{#  if(d.status == 0){ }}
                <button class=" layui-btn layui-btn-sm layui-btn-warm">禁用</button>
                {{#  } else if(d.status == 1) { }}
                <button class="layui-btn layui-btn-sm layui-btn-normal">启用</button>
                {{#  } else {  }}
                <button class="layui-btn layui-btn-sm layui-btn-danger">异常</button>
                {{#  } }}
            </script>

            <script type="text/html" id="table-useradmin-admin">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i
                        class="layui-icon layui-icon-edit"></i>详情</a>
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
        </div>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="buttonTp4">
    {{#  layui.each(d.roleList, function(index, item){ }}
    <span>{{ item.roleName }}</span>
    {{#  }); }}
</script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'table'], function () {
        var $ = layui.$
            , form = layui.form
            , table = layui.table;

        table.render({
            elem: '#LAY-user-back-manage'
            , url: '/user/list'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: '序号', sort: true}
                , {field: 'userName', width: 100, title: '登录名', align: 'center'}
                , {field: 'roleList', width: 200, title: '角色范围', align: 'center', templet: '#buttonTp4'}
                , {field: 'sex', title: '性别', templet: '#buttonTpl', width: 100, align: 'center'}
                , {field: 'phone', title: '手机', minWidth: 60, align: 'center'}
                , {field: 'email', title: '邮箱', minWidth: 60, align: 'center'}
                , {field: 'status', title: '审核状态', templet: '#buttonTp2', width: 100, align: 'center'}
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
            if (obj.event === 'delete') {  // 删除用户
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
                                layer.confirm('确定删除此用户吗？', function (index) {
                                    $.ajax({
                                        url: "/user/delete",
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
            } else if (obj.event === 'edit') { // 编辑按钮
                window.location.href = '/user/update/' + obj.data.id;
            } else if (obj.event === 'detail') { // 详情按钮
                layer.open({
                    type: 2
                    , maxmin: true
                    , title: '用户详细信息'
                    , content: '/user/' + obj.data.id
                    , area: ['480px', '450px']
                })
            }
        });


        var active = {
            batchDelete: function () { //批量删除用户
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
                                layer.confirm('确定删除这些用户吗？', function (index) {
                                    $.ajax({
                                        url: "/user/batchDelete",
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
            }

            , manAdd: function () {    // 添加用户
                window.location.href = '/view/user/admin/userAdd.html';
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


