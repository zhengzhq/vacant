<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseUrl"></c:url>
<html>
<head>
<title>登录</title>
</head>
<body>
	<form action="${baseUrl}/login" method="post">
		用户名： <input type="text" name="loginName"><br>
		密 码：<input type="password" name="password"><br>
		<input type="submit" value="登录" />
		<input type="reset" value="重置" />
	</form>
</body>
</html>
