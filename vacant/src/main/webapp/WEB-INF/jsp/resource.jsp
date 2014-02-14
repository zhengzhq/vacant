<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>TreeGrid ContextMenu - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/common.css">
<script type="text/javascript"
	src="${contextPath}/js/application/common.js"></script>
<script type="text/javascript"
	src="http://www.jeasyui.com/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
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
						<th data-options="field:'order',width:100,align:'center'">顺序</th>
						<th
							data-options="field:'progress',width:120,formatter:operationFmtr,align:'center'">操作</th>
					</tr>
				</thead>
			</table>
			<div id="mm" class="easyui-menu" style="width: 120px;">
				<div onclick="addResource()" data-options="iconCls:'icon-add'">新增</div>
				<div onclick="removeIt()" data-options="iconCls:'icon-remove'">删除</div>
				<div class="menu-sep"></div>
				<div onclick="collapse()">Collapse</div>
				<div onclick="expand()">Expand</div>
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
		function operationFmtr(value, row) {
			return '<a href="#">修改</a>';
		}
		function onContextMenu(e, row) {
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
		function addResource() {
			var node = $('#tg').treegrid('getSelected');
			$('#dlgEdit').dialog('open').dialog('setTitle', '添加资源');
			$('#fmEdit').form('clear');
			$('#parentId').val(node.id);
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

		function removeIt() {
			var node = $('#tg').treegrid('getSelected');
			if (node) {
				$('#tg').treegrid('remove', node.id);
			}
		}
	</script>
</body>
</html>