<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="<%=basePath%>jsppage/styles/common.css" />
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<style type="text/css">
fieldset {
	width: 600px;
	padding: 10px;
}

.msg2 {
	color: red;
	font-size: 20px;
}

.e_msg {
	color: red;
	font-size: 19px;
	display: none;
}
</style>
<script type="text/javascript">
	var employeename_flag = false;
	var username_flag = false;
	var userpwd_flag = false;
	var confirmpwd_flag = false;
	var phone_flag = false;
	var email_flag = false;

	function validEmployeename() {
		var employeename = document.form.Name.value;
		if (employeename.length == 0) {
			document.getElementById("ee_msg").innerText = "姓名不能为空";
			document.getElementById("ee_msg").style.display = "inline";
			employeename_flag = false;
		} else {
			document.getElementById("ee_msg").innerText = "";
			document.getElementById("ee_msg").style.display = "none";
			employeename_flag = true;
		}
	}

	function validUserpwd() {
		var userpwd = document.form.UserPwd.value;
		if (userpwd.length == 0) {
			document.getElementById("up_msg").innerText = "密码不能为空";
			document.getElementById("up_msg").style.display = "inline";
			userpwd_flag = false;
		} else {
			document.getElementById("up_msg").innerText = "";
			document.getElementById("up_msg").style.display = "none";
			userpwd_flag = true;
		}
	}

	function validConfirmpwd() {
		var userpwd = document.form.UserPwd.value;
		var confirmpwd = document.form.confirmpwd.value;
		if (userpwd!=confirmpwd) {
			document.getElementById("cp_msg").innerText = "密码不一致";
			document.getElementById("cp_msg").style.display = "inline";
			confirmpwd_flag = false;
		} else {
			document.getElementById("cp_msg").innerText = "";
			document.getElementById("cp_msg").style.display = "none";
			confirmpwd_flag = true;
		}
	}

	function validPhone() {
		var phone = $("#phone").val();
		var ckp=/^1[3|4|5|7|8]\d{9}$/;
		if (phone.length == 0) {
			document.getElementById("ph_msg").innerText = "联系电话不能为空";
			document.getElementById("ph_msg").style.display = "inline";
			phone_flag = false;
		}else if(ckp.test(phone)==false){
			document.getElementById('ph_msg').innerText = "手机号码格式错误";
			document.getElementById('ph_msg').style.display = 'inline';
			phone_flag = false;
		}else {
			document.getElementById("ph_msg").innerText = "";
			document.getElementById("ph_msg").style.display = "none";
			phone_flag = true;
		}
	}

	function validEmail() {
		var email = $("#email").val();
		var ckm = /^[A-Za-z0-9]\w+@\w+\.(com|cn|org|net)$/
		if (email.length == 0) {
			document.getElementById("em_msg").innerText = "电子邮件不能为空";
			document.getElementById("em_msg").style.display = "inline";
			email_flag = false;
		}else if(ckm.test(email)==false){
			document.getElementById("em_msg").innerText="邮箱格式不正确";
			document.getElementById("em_msg").style.display="inline";
			email_flag = false;
		}else {
			document.getElementById("em_msg").innerText = "";
			document.getElementById("em_msg").style.display = "none";
			email_flag = true;
		}
	}
	
	
	
	function validUsername(){
		var accountname = $("#accountname").val();
		var data = "UserName=" + accountname;
		var url = "${pageContext.request.contextPath}/user/valiUserName.action";
		$.ajax({
			data:data,
			type:"post",
			url:url,
			success:function(result) {
				if(result == 0) {
					username_flag = false;
					$("#un_msg").html("<font color='red' size='3'>用户名已存在</font>");
				} 
				if(result == 1) {
					username_flag = true;
					$("#un_msg").html("<font color='green' size='3'>用户名可以使用</font>");
				}
			}
		});
	}
	
	
	
	window.onload = function() {
		document.form.onsubmit = function() {
			return employeename_flag && username_flag && confirmpwd_flag
					&& userpwd_flag && phone_flag && email_flag;
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
		<div class="content-nav">人员管理 > 员工注册</div>
		<form name="form" action="${pageContext.request.contextPath}/user/register.action" method="post">
			<fieldset>
				<legend>员工信息</legend>
				<table class="formtable" style="width: 100%;">
					<tr>
						<td width="100px">姓名：</td>
						<td><input type="text" id="name" maxlength="20"
							name="Name" onblur="validEmployeename()" /> <label
							id="ee_msg" class="e_msg"></label></td>
					</tr>
					<tr>
						<td>账户名：</td>
						<td><input type="text" id="accountname" maxlength="20"
							name="UserName" onblur="validUsername()" /> <label id="un_msg"></label></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" id="password" maxlength="20"
							placeholder="请输入6位以上" name="UserPwd" onblur="validUserpwd()" /> <label
							id="up_msg" class="e_msg"></label></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input type="password" id="confirm" maxlength="20"
							name="confirmpwd" onblur="validConfirmpwd()" /> <label
							id="cp_msg" class="e_msg"></label></td>
					</tr>
					<tr>
						<td>联系电话：</td>
						<td><input type="text" id="phone" maxlength="20" name="phone"
							onblur="validPhone()" /> <label id="ph_msg" class="e_msg"></label>
						</td>
					</tr>
					<tr>
						<td>电子邮件：</td>
						<td><input type="text" id="email" maxlength="20" name="Email"
							onblur="validEmail()" /> <label id="em_msg" class="e_msg"></label>
						</td>
					</tr>
					<tr align="center">
						<td colspan="3"><label class="msg2">${requestScope.msg}</label></td>
					</tr>
					<tr>
						<td colspan="6" class="command"><input type="submit"
							class="clickbutton" value="注册" /> <input type="reset"
							class="clickbutton" value="重置" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div class="page-footer">
		<hr />
		更多问题，欢迎联系<a href="mailto:webmaster@eeg.com">管理员</a> <img
			src="<%=basePath%>jsppage/images/header.png" alt="CoolMeeting" />
	</div>
</body>
</html>