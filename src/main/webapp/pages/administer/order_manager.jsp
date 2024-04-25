<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单管理</title>
<%@ include file="/pages/common/head.jsp" %>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">订单管理系统</span>
			<%@ include file="/pages/common/manager_menu.jsp" %>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>日期</td>
				<td>金额</td>
				<td>详情</td>
				<td>发货</td>
				
			</tr>
			<c:if test="${not empty requestScope.allOrder}">
			    <c:forEach items="${requestScope.allOrder}" var="order">
                    <tr>
                        <td>${fn:substring(order.createTime,0,10)}</td>
                        <td>${order.price}</td>
                        <td><a href="orderServlet?action=showOrderDetail&orderId=${order.orderId}">查看详情</a></td>
                        <td>
                            <%--待发货--%>
                            <c:choose>
                                <c:when test="${order.status == 1}">
                                    <a href="orderServlet?action=sendOrder&orderId=${order.orderId}">点击发货</a>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${order.status == 0}">
                                            待支付
                                        </c:when>
                                        <c:when test="${order.status == 2}">
                                            待收货
                                        </c:when>
                                        <c:when test="${order.status == 3}">
                                            已签收
                                        </c:when>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
			</c:if>
		</table>
	</div>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>