<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>支付</title>
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
    <span class="wel_word">支付</span>
    <%@ include file="/pages/common/login_success_menu.jsp" %>
</div>

<div id="main">

    <div align="center" height="50%">
        <h1 color="red">
            <a href="orderServlet?action=payOrder&orderId=${sessionScope.orderId}">支付</a>
        </h1>
    </div>

</div>
<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>