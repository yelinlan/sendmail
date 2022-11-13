<%--
  Created by IntelliJ IDEA.
  User: 夜林蓝
  Date: 2022/11/13
  Time: 0:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
</head>
<body>
hello
<form method="post" action="${pageContext.request.contextPath}/register">
    <span>用户名：<input type="text" name="username"/></span><br>
    <span>密码：<input type="password" name="password"/></span><br>
    <span>邮箱：<input type="text" name="mail"/></span><br>
    <span><input type="submit" value="注册"/> | <input type="reset"/></span><br>
    <span> 消息：${pageContext.request.getAttribute("msg")}</span><br>
</form>
</body>
</html>
