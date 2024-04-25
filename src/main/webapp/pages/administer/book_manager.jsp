<%--suppress Annotator --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!--EL表达式支持-->
    <%@ page isELIgnored="false" %>
    <!-- JSTL 核心标签库 -->
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <!DOCTYPE html>
    <html>
    <head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <%@ include file="/pages/common/head.jsp" %>
    <script type="text/javascript">
        //页面加载之后
        $(function(){
            //使用Jquery查找删除的a标签
            $("a.deleteClass").click(function(){
                 //获取要删除的书籍名称
                return confirm("确定要删除【" + $(this).parent().parent().find("td:first").text() + "】吗");
            })

            //分页确认按钮绑定单击事件
            $("#btnPage").click(function(){
                //获取控件的值
                var pageNo = $("#pageNo").val();
                var pageSize = $("#pageSize").val();

                //basePath从pageContext域中取出
                location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo + "&pageSize=" + pageSize;

            })
        });
    </script>
    </head>
    <body>

        <div id="header">
                <img class="logo_img" alt="" src="static/img/logo.gif" >
                <span class="wel_word">图书管理系统</span>
                <%@ include file="/pages/common/manager_menu.jsp" %>
        </div>

        <div id="main">
            <table>
                <tr>
                    <td>名称</td>
                    <td>价格</td>
                    <td>作者</td>
                    <td>销量</td>
                    <td>库存</td>
                    <td colspan="2">操作</td>
                </tr>
                <!-- 使用JSTL进行书籍信息的遍历
                       bookList的名称要和BookServlet的setAttribute的属性名称一致
                -->
                <!--没有分页
                <c:forEach items="${requestScope.bookList}" var="book">
                    <tr>
                        <td>${book.name}</td>
                        <td>${book.price}</td>
                        <td>${book.author}</td>
                        <td>${book.sales}</td>
                        <td>${book.stock}</td>
                        <%--区分是修改图书操作还是添加图书操作？解决方案1：在这里追加methord请求参数--%>
                        <td><a href="manager/bookServlet?action=queryBook&methord=update&id=${book.id}">修改</a></td>
                        <td><a class="deleteClass" href="manager/bookServlet?action=delete&id=${book.id}">删除</a></td>
                    </tr>
                </c:forEach>
                -->
                <!--
                    分页实现
                -->
                <c:forEach items="${requestScope.page.items}" var="book">
                                <tr>
                                    <td>${book.name}</td>
                                    <td>${book.price}</td>
                                    <td>${book.author}</td>
                                    <td>${book.sales}</td>
                                    <td>${book.stock}</td>
                                    <!--区分是修改图书操作还是添加图书操作？解决方案1：在这里追加methord请求参数-->
                                    <td><a href="administer/bookServlet?action=queryBook&methord=update&id=${book.id}&pageNo=${requestScope.page.pageNo}">修改</a></td>
                                    <td><a class="deleteClass" href="administer/bookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageNo}">删除</a></td>
                                </tr>
                            </c:forEach>

                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <!--区分是修改图书操作还是添加图书操作？解决方案1：在这里追加methord请求参数-->
                    <td><a href="pages/administer/book_edit.jsp?methord=add&pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
                </tr>
            </table>

            <!--分页静态包含-->
            <%@ include file="/pages/common/page_nav.jsp" %>

        </div>
        <%@ include file="/pages/common/footer.jsp" %>
    </body>
    </html>