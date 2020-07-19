<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统题库</title>
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
                        <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input"
                               style="width: 300px;">
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

                <div class="layui-inline" style="width: 250px">
                    <label class="layui-form-label">适用年级</label>
                    <div class="layui-input-block">
                        <select name="grade">
                            <option value="">全部</option>
                            <option value="0">一年级</option>
                            <option value="1">二年级</option>
                            <option value="2">三年级</option>
                            <option value="3">四年级</option>
                            <option value="4">五年级</option>
                            <option value="5">六年级</option>
                            <option value="6">初一</option>
                            <option value="7">初二</option>
                            <option value="8">初三</option>
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
                <shiro:hasRole name="teacher">
                    <shiro:hasPermission name="add_question_from_sys">
                        <button class="layui-btn layui-btn-radius" data-type="choose">导入我的题库</button>
                    </shiro:hasPermission>
                </shiro:hasRole>

                <shiro:hasRole name="admin">
                    <button class="layui-btn layui-btn-danger layui-btn-radius" data-type="batchDelete">批量删除</button>
                    <button class="layui-btn layui-btn-radius" data-type="manAdd">手动导入</button>
                    <button class="layui-btn layui-btn-radius" data-type="fileAddByAdmin">文件导入</button>
                    <button class="layui-btn layui-btn-radius" data-type="ocrAdd">OCR文字识别</button>
                </shiro:hasRole>
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

            <script type="text/html" id="buttonTp3">
                {{#  if(d.grade == 0){ }}
                <button class="layui-btn layui-btn-sm">一年级</button>
                {{#  } else if(d.grade == 1) { }}
                <button class="layui-btn layui-btn-sm">二年级</button>
                {{#  } else if(d.grade == 2) { }}
                <button class="layui-btn layui-btn-sm">三年级</button>
                {{#  } else if(d.grade == 3) { }}
                <button class="layui-btn layui-btn-sm">四年级</button>
                {{#  } else if(d.grade == 4) { }}
                <button class="layui-btn layui-btn-sm">五年级</button>
                {{#  } else if(d.grade == 5) { }}
                <button class="layui-btn layui-btn-sm">六年级</button>
                {{#  } else if(d.grade == 6) { }}
                <button class="layui-btn layui-btn-sm">初&nbsp;&nbsp;&nbsp;&nbsp;一</button>
                {{#  } else if(d.grade == 7) { }}
                <button class="layui-btn layui-btn-sm">初&nbsp;&nbsp;&nbsp;&nbsp;二</button>
                {{#  } else if(d.grade == 8) { }}
                <button class="layui-btn layui-btn-sm">初&nbsp;&nbsp;&nbsp;&nbsp;三</button>
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

                <shiro:hasRole name="admin">
                    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                            class="layui-icon layui-icon-edit"></i>编辑</a>

                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i
                            class="layui-icon layui-icon-delete"></i>删除</a>
                </shiro:hasRole>

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
    }).use(['index', 'table', 'admin'], function () {
        var $ = layui.$
            , form = layui.form
            , table = layui.table
            , admin = layui.admin;

        table.render({
            elem: '#LAY-user-back-manage'
            , url: '/question/list/system'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: '题号', sort: true}
                , {field: 'name', width: 300, title: '题目', event: 'name', style: 'cursor: pointer;'}
                , {field: 'ops', width: 250, title: '选项', templet: '#buttonTp4', align: 'center'}
                , {field: 'type', title: '类型', templet: '#buttonTpl', minWidth: 60, align: 'center'}
                , {field: 'difficulty', title: '难度', templet: '#buttonTp2', minWidth: 60, align: 'center', sort: true}
                , {field: 'grade', title: '适用年级', templet: '#buttonTp3', minWidth: 60, align: 'center', sort: true}
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
            if (obj.event === 'delete') {  // 删除错题
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
                                layer.confirm('确定删除这道错题吗？', function (index) {
                                    $.ajax({
                                        url: "/question/delete/system",
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
                                                    , content: "删除失败,稍候再试"
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
                var id = obj.data.id;
                layer.open({
                    type: 2
                    , title: '修改错题'
                    , content: '/question/system/update/' + id
                    , shadeClose: true
                    , maxmin: true
                    , area: admin.screen() < 2 ? ['100%', '80%'] : ['630px', '550px']
                    , btn: ['确定', '取消']
                    , yes: function (index, layero) {
                        var iframeWindow = window['layui-layer-iframe' + index]
                            , submitID = 'LAY-user-back-submit'
                            , submit = layero.find('iframe').contents().find('#' + submitID),
                            listenID = 'LAY-user-front-submit';

                        iframeWindow.layui.form.on('submit(' + listenID + ')', function (data) {
                            var field = data.field;
                            $.ajax({
                                url: "/question/system/update",
                                data: {
                                    "id": id,
                                    "difficulty": field.difficulty,
                                    "grade": field.grade
                                },
                                success: function (res) {
                                    layer.close(index);
                                    if (res.code === 0) {
                                        layer.open({
                                            title: "提示"
                                            , content: "修改成功"
                                        });
                                    } else {
                                        layer.open({
                                            title: "提示"
                                            , content: "修改失败,稍候再试"
                                        });
                                    }
                                },
                                error: function () {
                                    layer.close(index);
                                    location.href = '/view/tips/error.html'
                                }
                            });
                            table.reload('LAY-user-back-manage', {
                                page: {
                                    curr: 1
                                }
                            });
                        });
                        submit.trigger('click');
                    }
                })
            } else if (obj.event === 'detail') { // 详情按钮
                layer.open({
                    title: '错题详情'
                    , type: 2
                    , content: '/question/system/' + obj.data.id
                    , shadeClose: true
                    , area: admin.screen() < 2 ? ['100%', '80%'] : ['630px', '450px']
                    , maxmin: true
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
            batchDelete: function () { //批量删除错题
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
                                layer.confirm('确定删除这些错题吗？', function (index) {
                                    $.ajax({
                                        url: "/question/batchDelete/system",
                                        data: {"ids": idArr},
                                        traditional: true
                                    });
                                    table.reload('LAY-user-back-manage', {
                                        page: {
                                            curr: 1
                                        }
                                    });
                                    layer.close(index);
                                });
                            } else {
                                layer.open({
                                    title: "提示"
                                    , content: "密码错误，不允许"
                                });
                            }
                        },
                        error:function () {
                            location.href = '/view/tips/error.html';
                        }
                    });
                });
            }, choose: function () {  //教师导入题库错题到自己的错题列表中
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
                    url: "/question/import/from/system",
                    data: {"ids": idArr},
                    traditional: true,
                    success: function (res) {
                        if (res.code === 0) {
                            layer.msg('导入成功,请前往个人题库中查看', {
                                offset: '15px'
                                , icon: 1
                                , time: 2000
                            });
                        } else {
                            layer.msg('导入失败,请稍候再试', {
                                offset: '15px'
                                , icon: 1
                                , time: 2000
                            });
                        }
                    },
                    error: function () {
                        location.href = '/view/tips/error.html';
                    }
                });
            }, fileAddByAdmin: function () {    // 管理员从文件导入错题
                layer.open({
                    type: 2
                    , title: '从word文档导入错题'
                    , content: '/view/question/admin/addQuesByAdmin.html'
                    , maxmin: true
                    , area: ['850px', '550px']
                    , btn: ['确定', '取消']
                    , yes: function (index) {
                        layer.close(index);
                        table.reload('LAY-user-back-manage', {
                            page: {
                                curr: 1
                            }
                        });
                    }
                });
            }, ocrAdd: function () {    // OCR文字识别导入错题
                layer.open({
                    type: 2
                    , title: 'OCR文字识别'
                    , content: '/view/question/addQuestionOCR.jsp'
                    , maxmin: true
                    , area: ['600px', '650px']
                    , btn: ['添加', '取消']
                    , yes: function (index, layero) {
                        var iframeWindow = window['layui-layer-iframe' + index]
                            , submitID = 'LAY-user-back-submit'
                            , submit = layero.find('iframe').contents().find('#' + submitID),
                            listenID = 'LAY-user-front-submit';

                        iframeWindow.layui.form.on('submit(' + listenID + ')', function (data) {
                            var field = data.field;
                            var name = $.trim(field.name);

                            if (name === "") {
                                layer.msg('请输入题目', {
                                    offset: '15px'
                                    , icon: 1
                                    , time: 2000
                                });
                                return false;
                            }

                            $.ajax({
                                url: "/question/system/add",
                                data: {
                                    "name": name,
                                    "type": field.type,
                                    "difficulty": field.difficulty,
                                    "grade": field.grade
                                },
                                async: false,
                                success: function (res) {
                                    layer.close(index);
                                    if (res.code === 0) {
                                        layer.open({
                                            title: "提示"
                                            , content: "添加成功"
                                        });
                                    } else {
                                        layer.open({
                                            title: "提示"
                                            , content: "添加失败:重复添加了"
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
                        submit.trigger('click');
                    }
                });
            }, manAdd: function () {    // 手动输入错题
                layer.open({
                    type: 2
                    , title: '手动输入错题'
                    , content: '/view/question/manAddQuestion.jsp'
                    , maxmin: true
                    , area: ['700px', '550px']
                    , btn: ['添加', '取消']
                    , yes: function (index, layero) {
                        var iframeWindow = window['layui-layer-iframe' + index]
                            , submitID = 'LAY-user-back-submit'
                            , submit = layero.find('iframe').contents().find('#' + submitID),
                            listenID = 'LAY-user-front-submit';

                        iframeWindow.layui.form.on('submit(' + listenID + ')', function (data) {
                            var field = data.field;
                            var name = $.trim(field.name);

                            if (name === "") {
                                layer.msg('请输入题目', {
                                    offset: '15px'
                                    , icon: 1
                                    , time: 2000
                                });
                                return false;
                            }

                            $.ajax({
                                url: "/question/system/add",
                                data: {
                                    "name": name,
                                    "type": field.type,
                                    "difficulty": field.difficulty,
                                    "grade": field.grade
                                },
                                success: function (res) {
                                    layer.close(index);
                                    if (res.code === 0) {
                                        layer.open({
                                            title: "提示"
                                            , content: "添加成功"
                                        });
                                    } else {
                                        layer.open({
                                            title: "提示"
                                            , content: "添加失败:重复添加了"
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
                        submit.trigger('click');
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


