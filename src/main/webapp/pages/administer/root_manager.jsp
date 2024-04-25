<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--EL表达式支持-->
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统管理员管理</title>
    <%@ include file="/pages/common/head.jsp" %>
    <style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" >
    <span class="wel_word">系统管理员</span>
    <%@ include file="/pages/common/root_menu.jsp" %>
</div>

<div id="main">
    <h1 style="color: red">欢迎进入系统管理页面</h1>
</div>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>