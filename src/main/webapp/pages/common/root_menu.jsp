<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--EL表达式支持-->
<%@ page isELIgnored="false" %>
<div>
    <a href="adminServlet?action=queryManagerList&permissionType=系统管理员">系统管理员管理 | </a>
    <a href="adminServlet?action=queryManagerList&permissionType=管理员">普通管理员管理 | </a>
    <a href="pages/administer/manager.jsp">后台管理首页</a>
</div>
