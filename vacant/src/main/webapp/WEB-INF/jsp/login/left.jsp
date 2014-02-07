<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>空虚的心灵</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript"
	src="${contextPath}/js/application/common.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery-1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="easyui-tabs" style="width: 285; height: 535">
		<div title="菜单" style="padding: 10px">
			<ul id="tree"></ul>
		</div>
		<div title="常用" style="padding: 10px">
			<p style="font-size: 14px">jQuery EasyUI framework helps you
				build your web pages easily.</p>
			<ul>
				<li>easyui is a collection of user-interface plugin based on
					jQuery.</li>
				<li>easyui provides essential functionality for building modem,
					interactive, javascript applications.</li>
				<li>using easyui you don't need to write many javascript code,
					you usually defines user-interface by writing some HTML markup.</li>
				<li>complete framework for HTML5 web page.</li>
				<li>easyui save your time and scales while developing your
					products.</li>
				<li>easyui is very easy but powerful.</li>
			</ul>
		</div>
		<script type="text/javascript">
			$('#tree').tree({
				onClick : function(node) {
					var url = node.attributes.url;
					if (url.indexOf('#') != 0) {
						parent.frames['right'].location = contextPath + url;
					}
				},
				lines : true,
				data : $.parseJSON('${treeData}')
			});
		</script>
</body>
</html>