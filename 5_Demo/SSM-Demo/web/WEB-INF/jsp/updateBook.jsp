<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/1
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
    <!-- 引入 Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <%--正则表达式验证--%>
    <script>
        $(function () {
            $("#counts").blur(function () {
                var regx = new RegExp("^[0-9]*$");
                var counts = $("#counts").val();
                var legal = regx.test(counts);
                if (!legal) {
                    alert("检测到非法的库存输入, 请重新输入");
                    $("#counts").val("");
                }
            });
        });
    </script>
</head>
<body background="${pageContext.request.contextPath}/img/updatePage.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍信息</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/update" method="post">
        <%--隐藏域携带id信息--%>
        <input type="hidden" name="id" value="${book.id}">
        <%--数据回显--%>
        <div class="form-group">
            <label>书名: </label>
            <input type="text" name="name" class="form-control" value="${book.name}" required>
        </div>
        <div class="form-group">
            <label>库存: </label>
            <input id="counts" type="text" name="counts" class="form-control" value="${book.counts}" required>
        </div>
        <div class="form-group">
            <label>详情: </label>
            <input type="text" name="detail" class="form-control" value="${book.detail}" required>
        </div>
        <div class="form-group">
            <label>价格: </label>
            <input type="text" name="price" class="form-control" value="${book.price}" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="update">
        </div>
    </form>

</div>

</body>
</html>
