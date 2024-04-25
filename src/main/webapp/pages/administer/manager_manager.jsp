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
    <title>管理员管理</title>
    <%@ include file="/pages/common/head.jsp" %>
    <script type="text/javascript">
        //页面加载之后
        $(function(){
            //使用Jquery查找删除的a标签
            $("a.deleteClass").click(function(){
                 //获取要删除的书籍名称
                return confirm("确定要删除【" + $(this).parent().parent().find("td:first").text() + "】吗");
            })
        });
    </script>
    </head>
    <body>

        <div id="header">
                <img class="logo_img" alt="" src="static/img/logo.gif" >
                <span class="wel_word">管理员管理系统</span>
                <%@ include file="/pages/common/root_menu.jsp" %>
        </div>

        <div id="main">
            <table>
                <tr>
                    <td>用户名</td>
                    <td>密码</td>
                    <td>邮箱地址</td>
                    <td>注册时间</td>
                    <td>更新时间</td>
                    <td colspan="2">操作</td>
                </tr>
                <!-- 使用JSTL进行用户信息的遍历
                      userList的名称要和UserServlet的setAttribute的属性名称一致
                -->
                <c:forEach items="${requestScope.userList}" var="user">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>${user.email}</td>
                        <td>${user.registTime}</td>
                        <td>${user.updateTime}</td>
                        <td><a href="adminServlet?action=queryManagerOne&methord=update&username=${user.username}">修改</a></td>
                        <td><a class="deleteClass" href="adminServlet?action=delete&username=${user.username}">删除</a></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><a href="pages/administer/manager_edit.jsp?methord=add">添加管理员</a></td>
                </tr>
            </table>

        </div>
        <%@ include file="/pages/common/footer.jsp" %>
    </body>
    </html>