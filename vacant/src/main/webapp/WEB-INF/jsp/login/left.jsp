<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/include.jsp"%>
<body>
	<div class="easyui-accordion" fit="true">
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