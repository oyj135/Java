<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--EL表达式支持-->
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理员管理</title>
<%@ include file="/pages/common/head.jsp" %>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
	
	input {
		text-align: center;
	}

</style>
<script type="text/javascript">
	// 页面加载之后
	$(function(){
		// $("input[name='submit']").click(function(){
		//
		//
		// })

		//使用Jquery查找删除的a标签
		$("a.deleteClass").click(function(){
			//获取要删除的书籍名称
			return confirm("确定要删除【" + $(this).parent().parent().find("td:first").text() + "】吗");
		})
	});

	//当添加管理员提交时check有无内容
	function checkfun(){

		if($("input[name='action']").val() === "add"){
			let username = $("input[name='username']").val()
			let password = $("input[name='password']").val()
			let email = $("input[name='email']").val()
			let msg = ""
			if(username.length === 0){
				msg =  "用户名"
			}
			if(password.length === 0){
				if(msg.length > 0){
					msg = msg + ",密码"
				}else{
					msg = "密码"
				}
			}
			if(email.length === 0){
				if(msg.length > 0){
					msg = msg + ",邮件"
				}else{
					msg = "邮件"
				}
			}
			if(msg.length > 0){
				msg = msg + "不能为空，请输入值！！！"
				alert(msg)
				return false;//终止后面的submit处理
			}
		}
	};
</script>
</head>
<body>
<!--区分是修改图书操作还是添加图书操作？解决方案2：判断id的有无，有则为update，无则为add-->
<!--${empty param.id ? "add" : "update"}-->
<!--区分是修改图书操作还是添加图书操作？解决方案3：判断book的有无，有则为update，无则为add-->
<!--${empty requestScope.book ? "add" : "update"}-->

		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">管理员管理</span>
			<%@ include file="/pages/common/manager_menu.jsp" %>
		</div>
		
		<div id="main">
			<%--使用onsubmit事件函数控制表单的默认提交			--%>
			<form action="adminServlet" methord="get" onsubmit="return checkfun()">
			    <!--区分是修改管理员操作还是添加管理员操作？解决方案1：在这里获取manager_manager.jsp请求时追加的methord请求参数。使用el表达式获取。-->
			    <input type="hidden" name="action" value="${param.methord}"/>

				<table>
					<tr>
						<td>用户名</td>
						<td>用户密码</td>
						<td>邮箱地址</td>
						<td>注册时间</td>
						<td>更新时间</td>
						<td colspan="2">操作</td>
					</tr>		
					<tr>
						<td>
						<c:if test="${param.methord=='update'}">
							<label name="usernameLabel">${requestScope.user.username}</label>
							<!--修改操作时需要用户名，从manager_manager。jsp的请求中获取id值-->
							<input type="hidden" name="username" value="${param.username}"/>
<%--							<input name="username" type="hidden" value="${requestScope.user.username}"/>--%>
						</c:if>
						<c:if test="${param.methord=='add'}">
							<input name="username" type="text" value="${requestScope.user.username}"/>
						</c:if>
						</td>
						<td><input name="password" type="text" value="${requestScope.user.password}"/></td>
						<td><input name="email" type="text" value="${requestScope.user.email}"/></td>
						<td><label name="registTime">${requestScope.user.registTime}</label></td>
						<td><label name="updateTime">${requestScope.book.updateTime}</label></td>
						<td><input name="submit" type="submit" value="提交"/></td>
					</tr>	
				</table>
			</form>
			
	
		</div>
		<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>