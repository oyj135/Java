<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--EL表达式支持-->
<%@ page isELIgnored="false" %>
<div>
    <!--没有分页的情况下-->
    <!--<a href="manager/bookServlet?action=queryBookList">图书管理</a>-->
    <!--分页的情况-->
    <c:if test="${sessionScope.user.username=='root'}">
        <a href="pages/administer/root_manager.jsp">系统管理员 | </a>
    </c:if>
    <a href="administer/bookServlet?action=page">图书管理 | </a>
    <a href="orderServlet?action=showAllOrders">订单管理 | </a>
    <a href="adminServlet?action=logout">退出登录</a>
    <a href="index.jsp">返回首页</a>
</div>
