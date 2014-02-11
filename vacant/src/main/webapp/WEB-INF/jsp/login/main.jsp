<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>空灵</title>
</head>
	<!--cols定义了浏览器中frame的列数，可以用百分比，像素，*代表余下部分来设定，
        border表示设定frame之间的距离，framespacing也可以设定frame之间的宽度,
        scrolling决定frame是否可以使用滚动条，noresize决定frame是否可以改变大小，
        marginHeight设定在显示frame中的文字之前文字距离顶部及底部的空白距离
        marginWidth:设定在显示frame中的文字之前文字距离左右两边的空白距离,
        frameborder:决定是否在frame中显示边界，
    -->
<frameset border="0" noresize="yes" rows="100,*">
	<!--cols定义了浏览器中frame的列数，可以用百分比，像素，*代表余下部分来设定，
        border表示设定frame之间的距离，framespacing也可以设定frame之间的宽度,
        scrolling决定frame是否可以使用滚动条，noresize决定frame是否可以改变大小，
        marginHeight设定在显示frame中的文字之前文字距离顶部及底部的空白距离
        marginWidth:设定在显示frame中的文字之前文字距离左右两边的空白距离,
        frameborder:决定是否在frame中显示边界，
    -->
	<frame name="top" src="${contextPath}/top" />
	<frameset border="5" cols="300,*">
		<frame name="left" src="${contextPath}/left" />
		<frame name="right" src="${contextPath}/right" />
	</frameset>
</frameset>
</html>