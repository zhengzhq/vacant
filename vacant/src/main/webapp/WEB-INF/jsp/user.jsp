<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>空灵</title>
<%@ include file="/include.jsp"%>
</head>
<body>
	<div class="easyui-tabs" fit="true">
		<div title="用户管理" style="padding: 5px;">
			<table id="dg"></table>
			<div id="toolbar" style="padding: 3 0 0 5">
				<label>登录名:</label>
				<input id="loginName" style="width: 80px">
				<label>姓名:</label>
				<input id="name" style="width: 80px">
				<a href="#" class="easyui-linkbutton" plain="true"
					onclick="doSearch()" iconCls="icon-search">查询</a>
				<a href="#" class="easyui-linkbutton" plain="true"
					onclick="doStatistic()" iconCls="icon-sum">统计</a>
			</div>
			<div id="buttons">
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="newUser()" iconCls="icon-add">增加</a>
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="editUser()" iconCls="icon-edit">修改</a>
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="removeUser()" iconCls="icon-remove">注销</a>
			</div>
			<div id="dlg" class="easyui-dialog" fit="true"
				style="padding: 10px 20px" closed="true" buttons="#dlg-buttons">
				<div class="ftitle">用户信息</div>
				<form id="fm" method="post">
					<input type="hidden" name="id">
					<div class="fitem">
						<label>部门:</label>
						<input id="departmentId" name="departmentId"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>登录名:</label>
						<input name="loginName" class="easyui-validatebox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>密码:</label>
						<input name="password" class="easyui-validatebox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>姓名:</label>
						<input name="name" class="easyui-validatebox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>性别:</label>
						<input id="gender" name="gender" data-options="required:true">
					</div>
					<div class="fitem">
						<label>角色:</label>
						<input id="roleId" name="roleId" data-options="required:true">
					</div>
				</form>
			</div>
			<div id="dlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="saveUser()">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
			<!-- 注销 -->
			<div id="dlgRemove" class="easyui-dialog" fit="true"
				style="padding: 10px 20px" closed="true" buttons="#btnsRemove">
				<div class="ftitle">注销用户信息</div>
				<form id="fmRemove" method="post">
					<input type="hidden" name="id">
					<div class="fitem">
						<label>部门:</label>
						<input id="remove_department" readonly>
					</div>
					<div class="fitem">
						<label>登录名:</label>
						<input name="loginName" class="easyui-validatebox" readonly>
					</div>
					<div class="fitem">
						<label>密码:</label>
						<input name="password" class="easyui-validatebox" readonly>
					</div>
					<div class="fitem">
						<label>姓名:</label>
						<input name="name" class="easyui-validatebox" readonly>
					</div>
					<div class="fitem">
						<label>性别:</label>
						<input name="genderValue" readonly>
					</div>
					<div class="fitem">
						<label>角色:</label>
						<input id="remove_role" readonly>
					</div>
					<div class="fitem">
						<label>注销原因:</label>
						<input name="writtenOffReason" class="easyui-validatebox"
							data-options="required:true">
					</div>
				</form>
			</div>
			<div id="btnsRemove">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="doRemoveUser()">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#dlgRemove').dialog('close')">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var modelPath = contextPath + '/model1/';
		// uis begin
		$('#dg')
				.datagrid(
						{
							url : modelPath + 'query_page',
							toolbar : '#toolbar',
							pagination : true,
							rownumbers : true,
							singleSelect : true,
							fitColumns : true,
							loadMsg : '正在处理，请稍候...',
							columns : [ [
									{
										field : 'department',
										width : 1,
										title : '部门',
										halign : 'center',
										formatter : function(value, data) {
											if (data.department) {
												return data.department.name;
											}
										}
									},
									{
										field : 'loginName',
										width : 1,
										title : '登录名',
										halign : 'center',
										align : 'center'
									},
									{
										field : 'name',
										width : 1,
										title : '姓名',
										halign : 'center',
										align : 'center'
									},
									{
										field : 'genderValue',
										width : 1,
										title : '性别',
										halign : 'center',
										align : 'center'
									},
									{
										field : 'role',
										width : 1,
										title : '角色',
										halign : 'center',
										align : 'center',
										formatter : function(value, data) {
											if (data.role) {
												return data.role.name;
											}
										}
									},
									{
										field : 'operate',
										title : '操作',
										halign : 'center',
										align : 'center',
										formatter : function(value, data, index) {
											return '<a href="#" onclick="editUser2('
													+ index
													+ ')">修改</a>&nbsp;<a href="#" onclick="removeUser2('
													+ index + ')">注銷</a>';
										}
									} ] ]
						});
		$('#departmentId').combobox({
			required : true,
			editable : false,
			url : contextPath + '/json/department.json',
			panelHeight : 'auto'
		});
		$('#gender').combobox({
			required : true,
			editable : false,
			url : contextPath + '/json/gender.json',
			panelHeight : 'auto'
		});
		$('#roleId').combobox({
			required : true,
			editable : false,
			url : contextPath + '/json/role.json',
			panelHeight : 'auto'
		});
		// handlers begin
		function doSearch() {
			$('#dg').datagrid('load', {
				loginName : $('#loginName').val(),
				name : $('#name').val()
			});
		}
		function doStatistic() {
			$.messager.show({
				title : '提示',
				msg : '统计符合条件的记录数等，还没有实现呢'
			});
		}
		var pager = $('#dg').datagrid().datagrid('getPager'); // get the pager of datagrid
		pager.pagination({
			buttons : $('#buttons'),
			layout : [ 'prev', 'next' ],
			displayMsg : ''
		});
		function newUser() {
			$('#dlg').dialog('open').dialog('setTitle', '添加用户');
			$('#fm').form('clear');
		}
		function editUser() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('setTitle', '编辑用户');
				$('#fm').form('load', row);
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择要修改的用户'
				});
			}
		}
		function editUser2(index) {
			$('#dg').datagrid('selectRow', index);
			editUser();
		}
		function saveUser() {
			$('#fm').form('submit', {
				url : modelPath + 'save_user',
				method : 'post',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					result = $.parseJSON(result);
					if (result.message) {
						$.messager.show({
							title : '错误',
							msg : result.message
						});
					} else {
						$('#dlg').dialog('close');
						$('#dg').datagrid('reload');
					}
				}
			});
		}
		function removeUser() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlgRemove').dialog('open').dialog('setTitle', '注销用户');
				$('#fmRemove').form('clear');
				$('#fmRemove').form('load', row);
				$('#remove_department').val(row.department.name);
				$('#remove_role').val(row.role.name);
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择要注销的用户'
				});
			}
		}
		function removeUser2(index) {
			$('#dg').datagrid('selectRow', index);
			removeUser();
		}
		function doRemoveUser() {
			$('#fmRemove').form('submit', {
				url : modelPath + 'ajax/remove_user',
				method : 'post',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					result = $.parseJSON(result);
					if (result.message) {
						$.messager.show({
							title : '错误',
							msg : result.message
						});
					} else {
						$('#dlgRemove').dialog('close');
						$('#dg').datagrid('reload');
					}
				}
			});
		}
	</script>
</body>
</html>