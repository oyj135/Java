<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>黄金屋管理员登录页面</title>
    <%@ include file="/pages/common/head.jsp" %>
    <style>
        #remember{
            position: relative;
            top: 5px;
            margin-top: 10px;
            width: 20px;
            height: 20px;
            border-radius: 4px;
            background-color: #f6f6f6;
        }
    </style>
</head>
<body>
<div id="login_header">
    <img class="logo_img" alt="" src="static/img/logo.gif" >
</div>

<div class="login_banner">

    <div id="l_content">
        <span class="login_word">管理员登录</span>
    </div>

    <div id="content">
        <div class="login_form">
            <div class="login_box">
                <div class="tit">
                    <h1 >黄金屋管理员</h1>
                    <a href="index.jsp">返回首页</a>
                </div>
                <div class="msg_cont">
                    <b></b>
                    <span class="errorMsg">
                        <!-- 提示信息-->
                        ${requestScope.msg==null?"请输入管理员的用户名和密码！":requestScope.msg}
                    </span>
                </div>
                <div class="form">
                    <form action="adminServlet" method="post">
                        <!-- 优化处理 隐藏-->
                        <input type="hidden" name="action" value="adminLogin">
                        <label>用户名称：</label>
                        <!-- value属性的表达式用来当用户名和密码错误的时候显示用户名-->
                        <input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username"
                               value="${cookie.username_admin.value}"/>
                        <br />
                        <br />
                        <label>用户密码：</label>
                        <input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password"
                               value="${cookie.password_admin.value}"/>
                        <br>
                        <label>可以记住我哦：</label>
                        <input type="checkbox" name="remember" id="remember" value="6"/>
                        <br />
                        <br />
                        <input type="submit" value="登录" id="sub_btn" />
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>