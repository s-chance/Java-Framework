<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/1
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
    <style type="text/css">
      a {
        text-decoration: none;
        color: black;
        font-size: 18px;
      }
      h3 {
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: deepskyblue;
        border-radius: 4px;
      }
    </style>
  </head>
  <body background="${pageContext.request.contextPath}/img/index.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
    <h3>
      <a href="${pageContext.request.contextPath}/book/all">进入书籍列表页</a>
    </h3>
  </body>
</html>
