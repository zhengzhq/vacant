<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>TreeGrid ContextMenu - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../demo.css">
<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.min.js"></script>
<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>TreeGrid ContextMenu</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>Right click to display the context menu.</div>
	</div>
	<div style="margin: 10px 0;"></div>
	<table id="tg" class="easyui-treegrid" title="TreeGrid ContextMenu"
		style="width: 700px; height: 250px"
		data-options="
iconCls: 'icon-ok',
rownumbers: true,
animate: true,
collapsible: true,
fitColumns: true,
url: '/vacant/js/jquery-easyui-1.3.5/demo/treegrid/treegrid_data2.json',
method: 'get',
idField: 'id',
treeField: 'name',
onContextMenu: onContextMenu
">
		<thead>
			<tr>
				<th data-options="field:'name',width:180">Task Name</th>
				<th data-options="field:'persons',width:60,align:'right'">Persons</th>
				<th data-options="field:'begin',width:80">Begin Date</th>
				<th data-options="field:'end',width:80">End Date</th>
				<th
					data-options="field:'progress',width:120,formatter:formatProgress">Progress</th>
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
	<script type="text/javascript">
		function formatProgress(value) {
			if (value) {
				var s = '<div style="width:100%;border:1px solid #ccc">'
						+ '<div style="width:' + value
						+ '%;background:#cc0000;color:#fff">' + value + '%'
						+ '</div>'
				'</div>';
				return s;
			} else {
				return '';
			}
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