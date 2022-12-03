<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/3
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<div>
  <h1>上传通道一</h1>
  <form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="upload">
  </form>
</div>
<div>
  <h1>上传通道二</h1>
  <form action="${pageContext.request.contextPath}/upload2" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="upload">
  </form>
</div>
<br/>
<div>
  <h1>
    <a href="${pageContext.request.contextPath}/download">
      下载文件
    </a>
  </h1>
</div>
</body>
</html>
