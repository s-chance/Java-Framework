<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/2
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.3.1.min.js"></script>
    <script>
      function a() {
        $.post({
          url:"${pageContext.request.contextPath}/a1",
          data:{"name":$("#username").val()},
          success:function (data, status) {
            alert(data);
            console.log(data);
            console.log(status);
          }
        });
      }
    </script>
  </head>
  <body>
    <%--失去焦点事触发事件--%>
    name: <input type="text" id="username" onblur="a()">
  </body>
</html>
