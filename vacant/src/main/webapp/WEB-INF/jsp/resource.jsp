<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>空灵</title>
<%@ include file="/include.jsp"%>
</head>
<body>
	<div class="easyui-tabs" fit="true">
		<div title="资源管理" style="padding: 5px;">
			<table id="tg" class="easyui-treegrid" fix="true"
				data-options="rownumbers: true,animate: true,
						collapsible: true,fitColumns: true,
						url: '${contextPath}/resource/ajax/get_all_resources',
						method: 'get',idField: 'id',treeField: 'name',onContextMenu: onContextMenu">
				<thead>
					<tr>
						<th data-options="field:'name',width:100,halign:'center'">名称</th>
						<th data-options="field:'url',width:100,halign:'center'">Url</th>
						<th data-options="field:'isDisplay',width:100,align:'center'">是否是菜单项</th>
						<th data-options="field:'displayOrder',width:100,align:'center'">顺序</th>
						<th
							data-options="field:'progress',width:120,formatter:operationFmtr,align:'center'">操作</th>
					</tr>
				</thead>
			</table>
			<div id="mm" class="easyui-menu" style="width: 120px;">
				<div onclick="addTopResource()" data-options="iconCls:'icon-add'">添加顶级资源</div>
				<div onclick="addSubResource()" data-options="iconCls:'icon-add'">添加下级资源</div>
				<div onclick="editResource()" data-options="iconCls:'icon-add'">修改</div>
				<div onclick="removeResource()" data-options="iconCls:'icon-remove'">删除</div>
				<div class="menu-sep"></div>
				<div onclick="grantToAllRoles()">將此资源分配给所有角色</div>
			</div>
			<div id="dlgEdit" class="easyui-dialog" fit="true"
				style="padding: 10px 20px" closed="true" buttons="#btnsEdit">
				<form id="fmEdit" method="post">
					<input type="hidden" name="id">
					<input type="hidden" id="parentId" name="parentId">
					<div class="fitem">
						<label>名称:</label>
						<input name="name" class="easyui-validatebox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>URL:</label>
						<input name="url" class="easyui-validatebox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>顺序:</label>
						<input name="displayOrder" class="easyui-numberbox"
							data-options="required:true">
					</div>
					<div class="fitem">
						<label>是否是菜单项:</label>
						<input id="isDisplay" name="isDisplay"
							data-options="required:true">
					</div>
				</form>
			</div>
			<div id="btnsEdit">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="saveResource()">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#dlgEdit').dialog('close')">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var modelPath = contextPath + '/resource/';
		$('#isDisplay').combobox({
			required : true,
			editable : false,
			url : contextPath + '/json/yes_or_no.json',
			panelHeight : 'auto'
		});
		function operationFmtr(value, data, index) {
			return '<a href="#" onclick="editResource2(\''
					+ data.id
					+ '\')">修改</a>&nbsp;<a href="#" onclick="removeResource2(\''
					+ data.id + '\')">删除</a>';
		}
		function onContextMenu(e, row) {
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
		// 添加顶级资源
		function addTopResource() {
			$('#dlgEdit').dialog('open').dialog('setTitle', '添加资源');
			$('#fmEdit').form('clear');
		}
		function addSubResource() {
			var node = $('#tg').treegrid('getSelected');
			$('#dlgEdit').dialog('open').dialog('setTitle', '添加资源');
			$('#fmEdit').form('clear');
			$('#parentId').val(node.id);
		}

		function editResource() {
			var row = $('#tg').treegrid('getSelected');
			if (row) {
				$('#dlgEdit').dialog('open').dialog('setTitle', '修改资源');
				$('#fmEdit').form('load', row);
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择要修改的资源'
				});
			}
		}
		function editResource2(id) {
			$('#tg').treegrid('select', id);
			editResource();
		}
		function saveResource() {
			$('#fmEdit').form('submit', {
				url : modelPath + 'ajax/save_resource',
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
						$('#dlgEdit').dialog('close');
						$('#tg').treegrid('reload');
					}
				}
			});
		}
		// 删除资源
		// 如果存在下级资源，则不能删除
		function removeResource() {
			var node = $('#tg').treegrid('getSelected');
			if(node.children) {
				$.messager.show({
					title : '错误',
					msg : '存在下级资源，则不能删除'
				});
			}
			if (node) {
				if (confirm('确定要删除资源' + node.name + '吗？')) {
					$.post(modelPath + 'ajax/remove_resource',{id:node.id},
							function(result) {
						if (result.message) {
							$.messager.show({
								title : '错误',
								msg : result.message
							});
						} else {
							$('#tg').treegrid('reload');
						}
					});
				}
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择要删除的资源'
				});
			}
		}
		function removeResource2(id) {
			$('#tg').treegrid('select', id);
			removeResource();
		}
	</script>
</body>
</html>