<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layuiadmin/style/admin.css" media="all">
</head>
<body class="layui-layout-body">

<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <ul class="layui-nav layui-layout-left">
                <li class="layui-nav-item layadmin-flexible" lay-unselect>
                    <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" layadmin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
            </ul>

            <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
                <li class="layui-nav-item" lay-unselect>
                    <cite style="font-size: 30px; color: #0C0C0C">
                        欢迎 ${sessionScope.userInfo.userName}
                        <c:if test="${sessionScope.userInfo.roleList.get(0).id == 1}">
                            管理员
                        </c:if>
                        <c:if test="${sessionScope.userInfo.roleList.get(0).id == 2}">
                            教师
                        </c:if>
                        <c:if test="${sessionScope.userInfo.roleList.get(0).id == 3}">
                            学生
                        </c:if>
                        用户登录
                    </cite>
                    <dl class="layui-nav-child">
                        <dd><a lay-href="/view/user/userInfo.jsp">基本资料</a></dd>
                        <dd><a lay-href="/view/user/password.html">修改密码</a></dd>
                        <hr>
                        <dd style="text-align: center;"><a onclick="logOut()">退出</a></dd>
                    </dl>
                </li>
            </ul>
        </div>

        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu"
                    lay-filter="layadmin-system-side-menu">

                    <li data-name="order" class="layui-nav-item">
                        <shiro:hasPermission name="cat_question">
                            <a href="javascript:;" lay-tips="错题管理" lay-direction="2">
                                <i class="layui-icon layui-icon-home"></i>
                                <cite>错题管理</cite>
                            </a>
                        </shiro:hasPermission>

                        <shiro:hasRole name="student">
                            <shiro:hasPermission name="cat_question">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/student/stuQuestionList.jsp">我的错题列表</a>
                                    </dd>
                                </dl>
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/student/stuAnalysis.html">错题统计分析</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </shiro:hasRole>

                        <shiro:hasRole name="teacher">
                            <shiro:hasPermission name="cat_question">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/teacher/teaQuestionList.jsp">我上传的错题</a>
                                    </dd>
                                </dl>
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/teacher/stuQuesUnderTea.jsp">学生错题列表</a>
                                    </dd>
                                </dl>
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/teacher/teaAnalysis.html">学生错题统计分析</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </shiro:hasRole>

                        <shiro:hasAnyRoles name="admin, teacher">
                            <shiro:hasPermission name="cat_question">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/sysQuestionList.jsp">系统题库</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </shiro:hasAnyRoles>

                        <shiro:hasRole name="admin">
                            <shiro:hasPermission name="cat_question">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/question/admin/sysAnalysis.html">系统错题统计分析</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </shiro:hasRole>
                    </li>

                    <li data-name="order" class="layui-nav-item">
                        <shiro:hasPermission name="cat_practice">
                            <a href="javascript:;" lay-tips="练习管理" lay-direction="2">
                                <i class="layui-icon layui-icon-home"></i>
                                <cite>练习管理</cite>
                            </a>
                        </shiro:hasPermission>

                        <shiro:hasAnyRoles name="teacher, student">
                            <shiro:hasPermission name="cat_practice">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/practice/practiceList.jsp">我创建的练习</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>

                        </shiro:hasAnyRoles>

                        <shiro:hasRole name="student">
                            <shiro:hasPermission name="cat_practice">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/practice/student/practiceByTea.jsp">老师发布的练习</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </shiro:hasRole>

                        <shiro:hasRole name="admin">
                            <shiro:hasPermission name="cat_practice">
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/practice/admin/sysPracticeList.jsp">系统练习库</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </shiro:hasRole>
                    </li>

                    <shiro:hasRole name="admin">
                        <li data-name="order" class="layui-nav-item">
                            <shiro:hasPermission name="cat_user">
                                <a href="javascript:;" lay-tips="人员管理" lay-direction="2">
                                    <i class="layui-icon layui-icon-home"></i>
                                    <cite>人员管理</cite>
                                </a>
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/user/admin/userList.jsp">用户列表</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="cat_role">
                                <dl class="layui-nav-child">
                                    <dd>
                                        <a lay-href="/view/role/admin/roleList.jsp">角色管理</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </li>
                    </shiro:hasRole>

                    <shiro:hasRole name="admin">
                        <li data-name="order" class="layui-nav-item">
                            <shiro:hasPermission name="cat_log">
                                <a href="javascript:;" lay-tips="日志管理" lay-direction="2">
                                    <i class="layui-icon layui-icon-home"></i>
                                    <cite>日志管理</cite>
                                </a>
                                <dl class="layui-nav-child">
                                    <dd data-name="console">
                                        <a lay-href="/view/log/admin/logList.jsp">日志列表</a>
                                    </dd>
                                </dl>
                            </shiro:hasPermission>
                        </li>
                    </shiro:hasRole>

                </ul>
            </div>
        </div>

        <div class="layadmin-pagetabs" id="LAY_app_tabs">
            <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
            <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
                <ul class="layui-tab-title" id="LAY_app_tabsheader">
                    <li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i
                            class="layui-icon layui-icon-home"></i></li>
                </ul>
            </div>
        </div>

        <shiro:hasRole name="admin">
            <div class="layui-body" id="LAY_app_body">
                <div class="layadmin-tabsbody-item layui-show">
                    <iframe src="/view/question/admin/sysAnalysis.html" frameborder="0"
                            class="layadmin-iframe"></iframe>
                </div>
            </div>
        </shiro:hasRole>

        <shiro:hasRole name="teacher">
            <div class="layui-body" id="LAY_app_body">
                <div class="layadmin-tabsbody-item layui-show">
                    <iframe src="/view/question/teacher/teaAnalysis.html" frameborder="0"
                            class="layadmin-iframe"></iframe>
                </div>
            </div>
        </shiro:hasRole>

        <shiro:hasRole name="student">
            <div class="layui-body" id="LAY_app_body">
                <div class="layadmin-tabsbody-item layui-show">
                    <iframe src="/view/question/student/stuAnalysis.html" frameborder="0"
                            class="layadmin-iframe"></iframe>
                </div>
            </div>
        </shiro:hasRole>
    </div>
</div>

<script src="/layuiadmin/layui/layui.js"></script>
<script src="/view/js/jquery-3.3.1.js"></script>
<script>
    //用户登出
    function logOut() {
        $.ajax({
            url: "/user/logout",
            success: function (res) {
                if (res.code === 0) {
                    layer.msg('退出成功', {
                        offset: '15px'
                        , icon: 1
                        , time: 2000
                    }, function () {
                        location.href = '/view/user/login.html';
                    });
                } else {
                    layer.msg('退出失败,请稍候再试', {
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
    }
</script>
<script>
    layui.config({
        base: '/layuiadmin/'
    }).extend({
        index: 'lib/index'
    }).use(['index']);
</script>

</body>
</html>


