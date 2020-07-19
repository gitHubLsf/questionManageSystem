<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>老师布置的练习</title>
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
                    <label class="layui-form-label">试卷名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input"
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

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <script type="text/html" id="table-useradmin-admin">
                <shiro:hasPermission name="download_practice">
                    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="download"><i
                            class="layui-icon layui-icon-edit"></i>下载</a>
                </shiro:hasPermission>
            </script>
        </div>
    </div>
    <a href="" id="downloadExam"></a>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="createDateTpl">
    {{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}
</script>
<script type="text/html" id="buttonTp4">
    <shiro:hasRole name="teacher">
        {{#  layui.each(d.parts, function(index, item){ }}
        ({{ item.questionCount  }}道:
        {{# if (item.type == 0) { }}
        选择题
        {{#  } else if (item.type == 1) {  }}
        填空题
        {{#  } else if (item.type == 2) {  }}
        问答题
        {{#  }   }},
        难度:
        {{# if (item.difficulty == 0) { }}
        简单
        {{#  } else if (item.difficulty == 1) {  }}
        较难
        {{#  } else if (item.difficulty == 2) {  }}
        很难
        {{#  }   }},
        适合:
        {{# if (item.grade == 0) { }}
        一年级
        {{# } else if (item.grade == 1) { }}
        二年级
        {{# } else if (item.grade == 2) { }}
        三年级
        {{# } else if (item.grade == 3) { }}
        四年级
        {{# } else if (item.grade == 4) { }}
        五年级
        {{# } else if (item.grade == 5) { }}
        六年级
        {{# } else if (item.grade == 6) { }}
        初一
        {{# } else if (item.grade == 7) { }}
        初二
        {{# } else if (item.grade == 8) { }}
        初三
        {{# } }})
        {{#  }); }}
    </shiro:hasRole>
    <shiro:hasRole name="student">
        {{#  layui.each(d.parts, function(index, item){ }}
        ({{ item.questionCount  }}道:
        {{# if (item.type == 0) { }}
        选择题
        {{#  } else if (item.type == 1) {  }}
        填空题
        {{#  } else if (item.type == 2) {  }}
        问答题
        {{#  }   }},
        难度:
        {{# if (item.difficulty == 0) { }}
        简单
        {{#  } else if (item.difficulty == 1) {  }}
        较难
        {{#  } else if (item.difficulty == 2) {  }}
        很难
        {{# } }})
        {{#  }); }}
    </shiro:hasRole>
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
            , url: '/practice/list/underTea'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: 'ID', sort: true}
                , {field: 'name', width: 250, title: '练习名称'}
                , {field: 'parts', width: 450, title: '详细信息', templet: "#buttonTp4"}
                , {
                    field: 'create_time',
                    title: '修改时间',
                    templet: '#createDateTpl',
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
            if (obj.event === 'download') { // 下载按钮
                $('#downloadExam').attr("href", "/practice/download?id=" + obj.data.id).append('<span></span>');
                $('#downloadExam span').click();
            }
        });

        $('.layui-btn.layui-btn-radius').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>


