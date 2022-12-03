<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/3
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <h1>登录成功</h1>
    <h2>当前用户信息</h2>
    <h3>${loginInfo}</h3>
    <a href="${pageContext.request.contextPath}/user/logout">登出</a>
  </body>
</html>
