<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>黄金屋首页</title>
<%@ include file="/pages/common/head.jsp" %>

<script type="text/javascript">
        //页面加载之后
        $(function(){

            //分页确认按钮绑定单击事件
            $("#btnPage").click(function(){
                //获取控件的值
                var pageNo = $("#pageNo").val();
                var pageSize = $("#pageSize").val();

                //basePath从pageContext域中取出
                location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo + "&pageSize=" + pageSize;

            })

            //添加到购物车功能
            $("button.addToCart").click(function(){
                //alert("${basePath}cartServlet" + "action=addItem&goodsId=" + bookId)
                var bookId = $(this).attr("bookId");

                //首次添加购物车使用全局刷新，防止使用ajax后要表示的内容没有展现。
                if(${empty sessionScope.cart}){
                    //使用表单实现购物车功能
                    location.href = "${basePath}cartServlet?action=addItem&goodsId=" + bookId
                }else{
                    //使用Ajax实现购物车功能
                    $.getJSON("${basePath}cartServlet","action=ajaxAddItem&goodsId=" + bookId,function(data){
                        //更新部分画面
                        $("#totalCount").text("您的购物车中有" + data.totalCount + "件商品")
                        $("#lastGoodsName").text(data.lastGoodsName)
                    });
                }

            })

        });
</script>
</head>
<body>

	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">黄金屋</span>
			<div>
			    <%--如果用户没有登录显示登录和注册--%>
			    <c:if test="${empty sessionScope.user}">
				    <a href="pages/user/login.jsp">登录</a>
				    <a href="pages/user/regist.jsp">注册</a>
				</c:if>
				<%--如果用户已经登录显示用户信息--%>
				<c:if test="${not empty sessionScope.user}">
				    <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临黄金屋书城</span>
                    <a href="pages/order/order.jsp"></a>
                    <a href="userServlet?action=logout">注销</a>&nbsp;&nbsp;
				</c:if>
				<a href="pages/cart/cart.jsp">购物车</a>
				<a href="pages/administer/manager.jsp">后台管理</a>
			</div>
	</div>
	<div id="main">
		<div id="book" >
			<div class="book_cond">
				<form action="client/clientBookServlet" method="get">
				        <input type="hidden" name="action" value="pageByPrice">
					价格：<input id="minPrice" type="text" name="minPrice" value="${param.minPrice}"> 元 -
						<input id="maxPrice" type="text" name="maxPrice" value="${param.maxPrice}"> 元
						<input type="submit" value="查询" />
				</form>
			</div>

            <div style="text-align: center">
                <c:if test="${not empty sessionScope.cart.items}">
                    <span id="totalCount">您的购物车中有${sessionScope.cart.totalCount}件商品</span>
                    <div>
                        您刚刚将<span id="lastGoodsName" style="color:red">${sessionScope.lastGoodsName}</span>加入到了购物车中
                    </div>
                </c:if>
                <c:if test="${empty sessionScope.cart}">
                    <span style="color:red">您的购物车为空</span>
                </c:if>
            </div>

            <!--遍历图书信息-->
            <c:forEach items="${requestScope.page.items}" var="book">

                <div class="b_list">
                    <div class="img_div">
                        <img class="book_img" alt="" src="${book.imgPath}" />
                    </div>
                    <div class="book_info">
                        <div class="book_name">
                            <span class="sp1">书名:</span>
                            <span class="sp2">${book.name}</span>
                        </div>
                        <div class="book_author">
                            <span class="sp1">作者:</span>
                            <span class="sp2">${book.author}</span>
                        </div>
                        <div class="book_price">
                            <span class="sp1">价格:</span>
                            <span class="sp2">${book.price}</span>
                        </div>
                        <div class="book_sales">
                            <span class="sp1">销量:</span>
                            <span class="sp2">${book.sales}</span>
                        </div>
                        <div class="book_amount">
                            <span class="sp1">库存:</span>
                            <span class="sp2">${book.stock}</span>
                        </div>
                        <div class="book_add">
                            <button bookId="${book.id}" class="addToCart">加入购物车</button>
                        </div>
                    </div>
                </div>
	        </c:forEach>
		</div>

        <!--分页静态包含-->
        <%@ include file="/pages/common/page_nav.jsp" %>

	</div>
	
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>