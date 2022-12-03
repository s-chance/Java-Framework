<%--
  Created by IntelliJ IDEA.
  User: Entropy
  Date: 2022/12/3
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.3.1.min.js"></script>
    <script>
      $(function () {
        $("#btn").click(function () {
          $.post("${pageContext.request.contextPath}/a2",function (data) {
            console.log(data);
            var html = "";
            for (let i = 0; i < data.length; i++) {
              html+="<tr>" +
                      "<td>" + data[i].name + "</td>" +
                      "<td>" + data[i].age + "</td>" +
                      "<td>" + data[i].password + "</td>" +
                      "</tr>";
            }
            $("#content").html(html);
          });
        });
      });
    </script>
</head>
<body>
  <input type="button" id="btn" value="获取用户列表">
  <table width="80%" align="center">
    <tr>
      <th>name</th>
      <th>age</th>
      <th>password</th>
    </tr>
    <tbody id="content" align="center">
      <%--用jQuery构造DOM结构, 并接收后端数据--%>
    </tbody>
  </table>
</body>
</html>
