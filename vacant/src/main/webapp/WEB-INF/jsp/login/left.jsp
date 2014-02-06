<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>空虚的心灵</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/js/jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/js/jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript" src="${contextPath}/js/application/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
</head>
<body>
	<ul id="tree"></ul>
	<script type="text/javascript">
	 $('#tree').tree({
		 onClick : function(node) {
			 var url = node.attributes.url;
			 if(url.indexOf('#')!=0) {
				 parent.frames['right'].location = contextPath+url;
			 }
		 },
		 lines:true,
		 data : $.parseJSON('${treeData}')
	 });
	</script>
</body>
</html>