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
	<div class="easyui-tabs" fit="true">
		<div title="用户管理">
			<table id="dg"></table>
			<div id="toolbar" style="padding: 3 0 0 5">
				登录名: <input id="loginName" style="width: 80px">
				姓名: <input id="name" style="width: 80px"><a href="#"
					class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询</a>
			</div>
			<div id="buttons">
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="doSearch()" iconCls="icon-add">增加</a> <a 
					href="#" plain="true" class="easyui-linkbutton"
					onclick="doSearch()" iconCls="icon-remove">删除</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#dg').datagrid({
			url : '${contextPath}/model1/query_page',
			toolbar : '#toolbar',
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'department',
				width : 200,
				title : '部门',
				formatter : function(value, rec) {
					if (rec.department) {
						return rec.department.name;
					}
				}
			}, {
				field : 'loginName',
				width : 100,
				title : '登录名'
			}, {
				field : 'name',
				width : 100,
				title : '姓名'
			} ] ]
		});
		function doSearch() {
			$('#dg').datagrid('load', {
				name : $('#name').val(),
			});
		}
		var pager = $('#dg').datagrid().datagrid('getPager'); // get the pager of datagrid
		pager.pagination({
			buttons : $('#buttons')
		});
	</script>
</body>
</html>