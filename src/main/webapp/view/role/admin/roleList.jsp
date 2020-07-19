<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>角色管理</title>
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
                <div class="layui-inline" style="width: 250px">
                    <label class="layui-form-label">角色筛选</label>
                    <div class="layui-input-block">
                        <select name="id">
                            <option value="">全部角色</option>
                            <option value="1">管理员</option>
                            <option value="2">教师</option>
                            <option value="3">学生</option>
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

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <script type="text/html" id="table-useradmin-admin">
                <shiro:hasPermission name="edit_role">
                    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                            class="layui-icon layui-icon-edit"></i>编辑</a>
                </shiro:hasPermission>
            </script>

        </div>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="buttonTp4">
    {{#  layui.each(d.ps, function(index, item){ }}
    <span>{{ item.permissionDesc }}</span>
    {{#  }); }}
</script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index', 'table', 'admin', 'laydate'], function () {
        var $ = layui.$
            , form = layui.form
            , table = layui.table;

        table.render({
            elem: '#LAY-user-back-manage'
            , url: '/role/list'
            , cols: [[
                {field: 'id', width: 80, title: 'ID', sort: true, unresize: true}
                , {field: 'roleName', width: 300, title: '角色名'}
                , {field: 'ps', width: 350, title: '权限范围', align: 'center', templet: '#buttonTp4'}
                , {field: 'roleDesc', title: '具体描述', align: 'center'}
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
            if (obj.event === 'edit') { // 编辑按钮
                window.location.href = '/role/update/' + obj.data.id;
            }
        });

        $('.layui-btn.layuiadmin-btn-admin').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>


