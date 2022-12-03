<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/3
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <a href="${pageContext.request.contextPath}/t1">拦截器测试</a>
    <a href="${pageContext.request.contextPath}/user/toLogin">访问用户登录认证页面</a>
    <a href="${pageContext.request.contextPath}/user/success">直接访问成功登录页面</a>
  </body>
</html>
