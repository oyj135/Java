<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
<%@ include file="/pages/common/head.jsp" %>
</head>
<body>
	<div id="header">
        <img class="logo_img" alt="" src="static/img/logo.gif" >
        <span class="wel_word">购物车</span>
        <%@ include file="/pages/common/login_success_menu.jsp" %>

        <script type="text/javascript">
            //页面加载之后
            $(function(){
                //给购物车的删除添加点击事件
                $("a.deleteItem").click(function(){
                    //获取商品名称 goodsName是在a标签的自定义属性
                    var goodsName = $(this).attr("goodsName");
                    return confirm("确定要删除商品【" + goodsName + "】吗？");
                })

                //给清空购物车添加带按键事件
                $("#clearCart").click(function(){
                    return confirm("确定要清空购物车吗？");
                })

                //给商品件数更新注册事件(失去焦点事件)
                $(".updateGoodsCount").change(function(){
                    //获取商品名称
                    var goodsName = $(this).parent().parent().find("td:first").text();
                    //获取商品Id
                    var goodsId = $(this).attr("goodsId");

                    if(confirm("确定要将商品【" + goodsName + "】的数量修改为【" + this.value + "】吗？")){
                        location.href="${basePath}cartServlet?action=updateItem&goodsId="+ goodsId + "&goodsCount=" + this.value
                    }else{
                        this.value  = this.defaultValue;
                    }
                })
            })

        </script>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>

			<c:if test="${empty sessionScope.cart.items}">
			    <tr>
                    <td colspan="5"><a href="index.jsp">亲！购物车为空，快去浏览商品进行购买吧！</a></td>
                </tr>
			</c:if>
			<c:if test="${not empty sessionScope.cart.items}">
                <%--从session中取得购物车信息并进行遍历--%>
                <c:forEach items="${sessionScope.cart.items}" var="entry">
                    <tr>
                        <td>${entry.value.goodsName}</td>
                        <td>
                            <input goodsId="${entry.value.goodsId}" class="updateGoodsCount" type="text" value="${entry.value.goodsCount}" style="width:80px"/>
                        </td>
                        <td>${entry.value.price}</td>
                        <td>${entry.value.totalPrice}</td>
                        <td><a goodsName="${entry.value.goodsName}" class="deleteItem" href="cartServlet?action=deleteItem&goodsId=${entry.value.goodsId}">删除</a></td>
                    </tr>
                </c:forEach>
			</c:if>
		</table>

		<%--购物车为空的话下面不输出--%>
		<c:if test="${not empty sessionScope.cart.items}">
            <div class="cart_info">
                <span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
                <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
                <span id="clearCart" class="cart_span"><a href="cartServlet?action=clear">清空购物车</a></span>
                <span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
            </div>
		</c:if>
	
	</div>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>