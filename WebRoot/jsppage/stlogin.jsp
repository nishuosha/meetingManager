
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="jsppage/styles/common.css" />
<style type="text/css">
fieldset {
	width: 600px;
	padding: 10px;
}

.err_msg {
	color: red;
	font-size: 10px;
	display: none;
}

.msg2 {
	color: red;
	font-size: 20px;
}

#nimage {
	width:100px;
	height:40px;
}
</style>
<script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>
<script type="text/javascript">
	var user_flag = false;
	var pwd_flag = false;
	var img_flag = false;

	function validUser() {
		var username = $("#accountname").val();
		var ckn = /^.*[操|金三胖|你妈的|他妈的].*$/;
		if (ckn.test(username)) {
			document.getElementById("user_err_msg").innerText = "用户名不能包含敏感词";
			document.getElementById("user_err_msg").style.display = "inline";
			user_flag = false;
		} else if (username.length == 0) {
			document.getElementById("user_err_msg").innerText = "用户名不能为空";
			document.getElementById("user_err_msg").style.display = "inline";
			return ;
			user_flag = false;
		} 
		
		document.getElementById("user_err_msg").innerText = "";
		var data = "UserName=" + $("#accountname").val();
		var url = "${pageContext.request.contextPath}/user/valiUserName.action"
		$.ajax({
			
			data:data,
			type:"post",
			url:url,
			success:function(result) {
			
				if(result == 0) {
					user_flag = true;
					$("#usererr").html("");
				}
				if(result == 1) {
					$("#usererr").html("用户名不存在");
					user_flag = false;
				}	
			}
			
		});
	
		
		
	}

	function validPwd() {
		var userpwd = $("#password").val();
		if (userpwd.length == 0) {
			document.getElementById("pwd_err_msg").innerText = "密码不能为空";
			document.getElementById("pwd_err_msg").style.display = "inline";
			pwd_flag = false;
		} else {
			document.getElementById("pwd_err_msg").innerText = "";
			document.getElementById("pwd_err_msg").style.display = "none";
			pwd_flag = true;
		}
	}

	function changeImg() {
		$("#nimage").attr("src","${pageContext.request.contextPath}/user/code.action?data=" + new Date());
	}


	function valiImage() {
		var $code = "code=" + $("#code").val();
		var url = "${pageContext.request.contextPath}/user/valiCode.action"
		$.ajax({
		
			data:$code,
			type:"post",
			url:url,
			success:function(result) {
				if(result == 0) {
					img_flag = false;
					$("#image_msg").html("<font color='red' siez='3'>验证码错误</font>");
				}	
				if(result == 1) {
					img_flag = true;
					$("#image_msg").html("<font color='green' siez='3'>验证码正确</font>");
				}
			}
			
		});
		
	}

	window.onload = function() {
		document.form.onsubmit = function() {
			return user_flag && pwd_flag && img_flag;
		}
	}

</script>
</head>
<body>
	<div class="page-header">
		<div class="header-banner">
			<img src="<%=basePath%>jsppage/images/header.png" alt="CoolMeeting" />
		</div>
		<div class="header-title">欢迎访问Cool-Meeting会议管理系统</div>
	</div>
	<div class="page-content">
		<div class="content-nav">登录</div>
		<form action="${pageContext.request.contextPath}/user/login.action" name="form" method="post">
			<fieldset>
				<legend>登录信息</legend>
				<table class="formtable" style="width: 80%;">
					<tr>
						<td width="200px">账号名:</td>
						<td><input id="accountname" name="UserName" type="text"
							onblur="validUser()" /><label id="user_err_msg" class="err_msg"></label></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input id="password" name="UserPwd" type="password"
							onblur="validPwd()" /><label id="pwd_err_msg" class="err_msg"></label></td>
					</tr>
					<tr>
						<td>验证码：<a href="JavaScript:changeImg()"><img id="nimage"
								src="${pageContext.request.contextPath}/user/code.action"></a></td>
						<td><input type="text" id="code" name="code" onblur="valiImage()"
							style="width: 70px;"><label id="image_msg"></label></td>
					</tr>
					<tr align="center">
						<td colspan="2"><label class="msg2"><font color="red" id="usererr">
							<s:property value="#error"/>
						</font></label></td>
					</tr>
					<tr>
						<td colspan="2" class="command"><input type="submit"
							value="登录" class="clickbutton" /> <input type="button"
							value="返回" class="clickbutton" onclick="window.history.back();" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div class="page-footer">
		<hr />
		更多问题，欢迎联系<a href="mailto:webmaster@eeg.com">管理员</a> <img
			src="<%=basePath%>jsppage/images/footer.png" alt="CoolMeeting" />
	</div>
</body>
</html>