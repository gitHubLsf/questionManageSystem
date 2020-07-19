<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生上传的错题</title>
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
                    <label class="layui-form-label">题目</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input" style="width: 300px;">
                    </div>
                </div>

                <div class="layui-inline" style="width: 250px">
                    <label class="layui-form-label">类型</label>
                    <div class="layui-input-block">
                        <select name="type">
                            <option value="">全部</option>
                            <option value="0">选择题</option>
                            <option value="1">填空题</option>
                            <option value="2">问答题</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline" style="width: 200px">
                    <label class="layui-form-label">难度</label>
                    <div class="layui-input-block">
                        <select name="difficulty">
                            <option value="">全部</option>
                            <option value="0">简单</option>
                            <option value="1">较难</option>
                            <option value="2">很难</option>
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
                <shiro:hasPermission name="choose_question">
                    <button class="layui-btn layui-btn-radius" data-type="choose">抽题创建练习</button>
                </shiro:hasPermission>
            </div>

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <script type="text/html" id="buttonTpl">
                {{#  if(d.type == 0){ }}
                    <button class="layui-btn layui-btn-sm">选择题</button>
                {{#  } else if(d.type == 1) { }}
                    <button class="layui-btn layui-btn-sm">填空题</button>
                {{#  } else if(d.type == 2) { }}
                    <button class="layui-btn layui-btn-sm">问答题</button>
                {{#  } else {  }}
                    <button class="layui-btn layui-btn-sm layui-btn-danger">异常</button>
                {{#  } }}
            </script>

            <script type="text/html" id="buttonTp2">
                {{#  if(d.difficulty == 0){ }}
                    <button class="layui-btn layui-btn-sm layui-btn">简&nbsp;&nbsp;&nbsp;&nbsp;单</button>
                {{#  } else if(d.difficulty == 1) { }}
                    <button class="layui-btn layui-btn-sm layui-btn-normal">较&nbsp;&nbsp;&nbsp;&nbsp;难</button>
                {{#  } else if(d.difficulty == 2) { }}
                    <button class="layui-btn layui-btn-sm layui-btn-warm">很&nbsp;&nbsp;&nbsp;&nbsp;难</button>
                {{#  } else {  }}
                    <button class="layui-btn layui-btn-sm layui-btn-danger">异&nbsp;&nbsp;&nbsp;&nbsp;常</button>
                {{#  } }}
            </script>

            <script type="text/html" id="buttonTp4">
                {{#  layui.each(d.ops, function(index, item){ }}
                <span>{{ item.opt }}</span>
                {{#  }); }}
            </script>

            <script type="text/html" id="table-useradmin-admin">
                    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i
                            class="layui-icon layui-icon-edit"></i>详情</a>
            </script>
        </div>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
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
            , url: '/question/list/under/teacher'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: '题号', sort: true}
                , {field: 'name', width: 300, title: '题目', event: 'name', style: 'cursor: pointer;'}
                , {field: 'ops', width: 250, title: '选项', templet: '#buttonTp4', align: 'center'}
                , {field: 'type', title: '错题类型', templet: '#buttonTpl', minWidth: 60, align: 'center'}
                , {field: 'difficulty', title: '错题难度', templet: '#buttonTp2', minWidth: 60, align: 'center', sort: true}
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
           if (obj.event === 'detail') { // 详情按钮
                layer.open({
                    title:'错题详情'
                    ,type: 2
                    ,content: '/question/underTea/question/' + obj.data.id
                    ,shadeClose: true
                    ,area: admin.screen() < 2 ? ['100%', '80%'] : ['630px', '450px']
                    ,maxmin: true
                });
            } else if (obj.event === 'name') {
                var data1 = obj.data;
                layer.open({
                    formType: 2
                    , title: '查看题号为 [' + data1.id + '] 的题目内容'
                    , content: data1.name
                    , area: ['400px', '300px']
                });
            }
        });


        var active = {
            choose: function () { // 抽题组成练习
                var checkStatus = table.checkStatus('LAY-user-back-manage')
                    , checkData = checkStatus.data;

                if (checkData.length === 0) {
                    return layer.msg('请选择数据');
                }

                var idArr = [];
                for (var i = 0; i < checkData.length; i++) {
                    idArr[i] = checkData[i].id;
                }


                $.ajax({
                    url: "/question/choose",
                    data: {"ids": idArr},
                    traditional: true,
                    success:function (res) {
                        if (res.code === 0) {
                            layer.msg('创建练习成功,请前往练习列表查看', {
                                offset: '15px'
                                , icon: 1
                                , time: 2000
                            });
                        } else {
                            layer.msg('创建练习失败,请稍候再试', {
                                offset: '15px'
                                , icon: 1
                                , time: 2000
                            });
                        }
                    },
                    error:function () {
                        location.href = '/view/tips/error.html';
                    }
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


