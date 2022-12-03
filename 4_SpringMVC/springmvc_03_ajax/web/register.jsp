<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/3
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.3.1.min.js"></script>
    <script>
    function a1() {
        $.post({
          url:"${pageContext.request.contextPath}/a3",
          data:{'name':$("#name").val()},
          success:function (data) {
            if (data.toString() === 'PASS') {
              $("#userInfo").css("color", "green");
            } else {
              $("#userInfo").css("color", "red");
            }
            $("#userInfo").html(data);
          }
        });
      }
    function a2() {
      $.post({
        url:"${pageContext.request.contextPath}/a3",
        data:{'password':$("#password").val()},
        success:function (data) {
          if (data.toString() === 'PASS') {
            $("#passInfo").css("color", "green");
          } else {
            $("#passInfo").css("color", "red");
          }
          $("#passInfo").html(data);
        }
      });
    }
    </script>
</head>
<body>
  <p>
    用户名: <input type="text" id="name" onblur="a1()">
    <span id="userInfo"></span>
  </p>
  <p>
    密码: <input type="password" id="password" onblur="a2()">
    <span id="passInfo"></span>
  </p>
</body>
</html>
