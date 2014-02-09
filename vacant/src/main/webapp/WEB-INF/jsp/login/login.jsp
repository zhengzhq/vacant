<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>登录</title>
<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
<script type="text/javascript" src="${contextPath}/js/application/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
</head>
<body>
	<form id="form" action="<c:url value="/main" />" method="post">
		用户名：
		<input type="text" id="loginName" name="loginName">
		<br> 密 码：
		<input id="password" type="password" name="password">
		<br>
		<input type="button" value="登录" onclick="login()" />
		<input type="reset" value="重置" />
	</form>
	<script type="text/javascript">
		function login() {
			var data = {
				loginName : $('#loginName').val(),
				password : $('#password').val(),
			};
			$.ajax({
				type : "POST",
				url : contextPath + '/login_validate',
				data : data,
				async : false,
				success : function(res) {
					if(res.success) {
						$('#form').submit();
					} else {
						$.messager.show({
							title : '错误',
							msg : res.message
						});
					}
				},
				dataType : 'json'
			});
		}
	</script>
</body>
</html>
