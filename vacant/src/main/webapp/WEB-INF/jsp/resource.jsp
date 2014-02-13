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
						method: 'get',idField: 'id',treeField: 'displayName',onContextMenu: onContextMenu">
				<thead>
					<tr>
						<th data-options="field:'displayName',width:100,halign:'center'">名称</th>
						<th data-options="field:'url',width:100,halign:'center'">Url</th>
						<th data-options="field:'isDisplay',width:100,align:'center'">是否是菜单项</th>
						<th data-options="field:'displayOrder',width:100,align:'center'">顺序</th>
						<th
							data-options="field:'progress',width:120,formatter:operationFmtr,align:'center'">操作</th>
					</tr>
				</thead>
			</table>
			<div id="mm" class="easyui-menu" style="width: 120px;">
				<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
				<div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
				<div class="menu-sep"></div>
				<div onclick="collapse()">Collapse</div>
				<div onclick="expand()">Expand</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function operationFmtr(value,row) {
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
		var idIndex = 100;
		function append() {
			idIndex++;
			var d1 = new Date();
			var d2 = new Date();
			d2.setMonth(d2.getMonth() + 1);
			var node = $('#tg').treegrid('getSelected');
			$('#tg').treegrid('append', {
				parent : node.id,
				data : [ {
					id : idIndex,
					name : 'New Task' + idIndex,
					persons : parseInt(Math.random() * 10),
					begin : $.fn.datebox.defaults.formatter(d1),
					end : $.fn.datebox.defaults.formatter(d2),
					progress : parseInt(Math.random() * 100)
				} ]
			})
		}
		function removeIt() {
			var node = $('#tg').treegrid('getSelected');
			if (node) {
				$('#tg').treegrid('remove', node.id);
			}
		}
		function collapse() {
			var node = $('#tg').treegrid('getSelected');
			if (node) {
				$('#tg').treegrid('collapse', node.id);
			}
		}
		function expand() {
			var node = $('#tg').treegrid('getSelected');
			if (node) {
				$('#tg').treegrid('expand', node.id);
			}
		}
	</script>
</body>
</html>