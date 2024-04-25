<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
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
			<span class="wel_word">订单详情</span>
			<%@ include file="/pages/common/login_success_menu.jsp" %>
	</div>
	
	<div id="main">
		
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
			</tr>
            <c:forEach items="${requestScope.orderItemList}" var="orderItem">
                <tr>
                    <td>${orderItem.goodsName}</td>
                    <td>${orderItem.goodsCount}</td>
                    <td>${orderItem.price}</td>
                    <td>${orderItem.totalPrice}</td>
                </tr>
            </c:forEach>

		</table>

		<%--
        <c:if test="${not empty sessionScope.cart.items}">
            <div class="order_info">
                <span class="order_span">购物车中共有<span class="b_count"></span>件商品</span>
                <span class="order_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
            </div>
        </c:if>
        --%>

	
	</div>

	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>