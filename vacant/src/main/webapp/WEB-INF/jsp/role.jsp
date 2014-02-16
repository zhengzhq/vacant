<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/include.jsp"%>

<body>
	<div class="easyui-tabs" fit="true">
		<div title="角色管理" style="padding: 5px;">
			<table id="dg"></table>
			<div id="toolbar" style="padding: 3 0 0 5">
				<label>名称:</label>
				<input id="name" style="width: 80px">
				<a href="#" class="easyui-linkbutton" plain="true"
					onclick="doSearch()" iconCls="icon-search">查询</a>
				<a href="#" class="easyui-linkbutton" plain="true"
					onclick="doStatistic()" iconCls="icon-sum">统计</a>
			</div>
			<div id="buttons">
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="newRole()" iconCls="icon-add">增加</a>
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="editRole()" iconCls="icon-edit">修改</a>
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="removeRole()" iconCls="icon-remove">注销</a>
				<a href="#" plain="true" class="easyui-linkbutton"
					onclick="removeRole()" iconCls="icon-remove">删除</a>
			</div>
			<div id="dlg" class="easyui-dialog" fit="true"
				style="padding: 10px 20px" closed="true" buttons="#dlg-buttons">
				<form id="fm" method="post">
					<input type="hidden" name="id">
					<div class="fitem">
						<label>名称:</label>
						<input name="name" class="easyui-validatebox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>描述:</label>
						<input name="description" data-options="required:true">
					</div>
				</form>
			</div>
			<div id="dlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="saveRole()">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
			<!-- 注销 -->
			<div id="dlgRemove" class="easyui-dialog" fit="true"
				style="padding: 10px 20px" closed="true" buttons="#btnsRemove">
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
					iconCls="icon-ok" onclick="doRemoveRole()">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#dlgRemove').dialog('close')">取消</a>
			</div>
		</div>
		<!-- 第二个选项卡 -->
		<div title="已注销的角色" style="padding: 5px;"></div>
	</div>
	<script type="text/javascript">
		var modelPath = contextPath + '/role/';
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
										field : 'name',
										width : 1,
										title : '名称',
										halign : 'center'
									},
									{
										field : 'description',
										width : 1,
										title : '描述',
										halign : 'center'
									},
									{
										field : 'operate',
										title : '操作',
										halign : 'center',
										align : 'center',
										formatter : function(value, data, index) {
											return '<a href="#" onclick="editRole2('
													+ index
													+ ')">修改</a>';
										}
									} ] ]
						});
		// handlers begin
		function doSearch() {
			$('#dg').datagrid('load', {
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
		function newRole() {
			$('#dlg').dialog('open').dialog('setTitle', '添加角色');
			$('#fm').form('clear');
		}
		function editRole() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('setTitle', '编辑角色');
				$('#fm').form('load', row);
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择要修改的角色'
				});
			}
		}
		function editRole2(index) {
			$('#dg').datagrid('selectRow', index);
			editRole();
		}
		function saveRole() {
			$('#fm').form('submit', {
				url : modelPath + 'save_role',
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
		function removeRole() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlgRemove').dialog('open').dialog('setTitle', '注销角色');
				$('#fmRemove').form('clear');
				$('#fmRemove').form('load', row);
				$('#remove_department').val(row.department.name);
				$('#remove_role').val(row.role.name);
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择要注销的角色'
				});
			}
		}
		function removeRole2(index) {
			$('#dg').datagrid('selectRow', index);
			removeRole();
		}
		function doRemoveRole() {
			$('#fmRemove').form('submit', {
				url : modelPath + 'ajax/remove_role',
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