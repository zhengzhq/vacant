<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/include.jsp"%>

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
				password : $('#password').val()
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
