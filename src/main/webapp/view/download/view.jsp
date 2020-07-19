<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
</head>
<body style="padding-top:20px;">
<div style="padding-bottom:25px">
    <h2 style="text-align: center;"> ${paper.name }</h2>
    <hr/>
    <div>
        <ul style="list-style: none;">
            <c:forEach items="${paper.parts }" var="parts" varStatus="status">
                <li style="list-style: none;">
                    <div>
                        <div style="float:left;">
                            <h4>
                                <c:if test="${parts.type == 0}">
                                    选择题
                                </c:if>
                                <c:if test="${parts.type == 1}">
                                    填空题
                                </c:if>
                                <c:if test="${parts.type == 2}">
                                    问答题
                                </c:if>
                            </h4>
                        </div>
                        <div style="clear: both;"></div>
                    </div>
                    <ol>
                        <c:forEach items="${parts.questions }" var="question">
                            <li>${question.name}
                                <c:if test="${question.type == 0 }">
                                    <div>
                                        <ol type="A">
                                            <c:forEach items="${question.ops }" var="aa">
                                                ${aa.opt }<br>
                                            </c:forEach>
                                        </ol>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ol>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>