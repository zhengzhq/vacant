<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>空灵</title>
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/icon.css">

<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
<script type="text/javascript"
	src="${contextPath}/js/application/common.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery-1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="easyui-tabs" style="width: 1050; height: 534">
		<div title="用户管理" style="padding: 10px">
			<table id="dg"></table>
			<div id="toolbar">
				<div style="margin-bottom: 5px">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true"></a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true"></a>&nbsp;<a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
				</div>
				<div>
					name: <input id="name" style="width: 80px"><a href="#"
						class="easyui-linkbutton" plain="true" onclick="doSearch()"
						iconCls="icon-search">Search</a>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#dg').datagrid({
			url : '${contextPath}/model1/query_page',
			toolbar : '#toolbar',
			pagination : true,
			columns : [ [ {
				field : 'Department',
				title : 'Department',
				formatter : function(value, rec) {
					if (rec.department) {
						return rec.department.name;
					}
				}
			}, {
				field : 'loginName',
				title : 'Login Name'
			}, {
				field : 'name',
				title : 'Name'
			} ] ]
		});
		function doSearch() {
			$('#dg').datagrid('load', {
				name : $('#name').val(),
			});
		}
	</script>
</body>
</html>