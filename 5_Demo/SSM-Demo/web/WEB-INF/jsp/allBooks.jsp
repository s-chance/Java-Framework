<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/1
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍列表</title>
    <%--BootStrap美化--%>
    <!--引入BootStrap-->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body background="${pageContext.request.contextPath}/img/list.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div class="container">

    <div class="row clearfix">

        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表</small>
                </h1>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 column">
                <%--跳转到添加书籍页面--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAdd">新增书籍</a>
                <%--全部查询--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/all">显示全部书籍</a>
            </div>

            <div class="col-md-4 column"></div>
            <div class="col-md-4 column">
                <%--搜索框--%>
                <form action="${pageContext.request.contextPath}/book/queryByName" class="form-inline" method="post">
                    <input type="text" name="name" class="form-control" placeholder="请输入书名" required>
                    <input type="submit" value="查询" class="btn btn-primary">
                </form>
            </div>
        </div>

    </div>

    <div class="row clearfix">

        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>编号</th>
                        <th>书名</th>
                        <th>库存</th>
                        <th>详情</th>
                        <th>价格</th>
                        <th>操作</th>
                    </tr>
                </thead>

                <%--EL表达式遍历后端传递过来的数据--%>
                <taody>
                    <c:forEach var="book" items="${all}">
                        <tr>
                            <td>${book.id}</td>
                            <td>${book.name}</td>
                            <td>${book.counts}</td>
                            <td>${book.detail}</td>
                            <td>${book.price}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/toUpdate?id=${book.id}">修改</a>
                                &nbsp; | &nbsp;
                                <a href="${pageContext.request.contextPath}/book/delete/${book.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </taody>
            </table>
            <span style="color: red;font-weight: bold">${error}</span>
        </div>

    </div>

</div>
</body>
</html>
