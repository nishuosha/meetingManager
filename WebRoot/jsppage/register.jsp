<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="<%=basePath%>jsppage/styles/common.css" />
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
		var employeename = document.form.employeename.value;
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
		var userpwd = document.form.userpwd.value;
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
		var userpwd = document.form.userpwd.value;
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
		var phone = document.form.phone.value;
		var ckp=/^1[3|4|5|7|8]\d{9}$/;
		if (phone.length == 0) {
			document.getElementById("ph_msg").innerText = "联系电话不能为空";
			document.getElementById("ph_msg").style.display = "inline";
			phone_flag = false;
		}else if(ckp.test(phone)==false){
			document.getElementById('phone_err_msg').innerText = "手机号码格式错误";
			document.getElementById('phone_err_msg').style.display = 'inline';
			phone_flag = false;
		}else {
			document.getElementById("ph_msg").innerText = "";
			document.getElementById("ph_msg").style.display = "none";
			phone_flag = true;
		}
	}

	function validEmail() {
		var email = document.form.email.value;
		var ckm = /^[A-Za-z0-9]\w+@\w+\.(com|cn|org|net)$/
		if (email.length == 0) {
			document.getElementById("em_msg").innerText = "电子邮件不能为空";
			document.getElementById("em_msg").style.display = "inline";
			email_flag = false;
		}else if(ckm.test(mail)==false){
			document.getElementById("mail_err_msg").innerText="邮箱格式不正确";
			document.getElementById("mail_err_msg").style.display="inline";
			email_flag = false;
		}else {
			document.getElementById("em_msg").innerText = "";
			document.getElementById("em_msg").style.display = "none";
			email_flag = true;
		}
	}
	
	var xmlHttp;
	//创建XMLHttpRequest对象
	function createXMLHttpRequest(){
		if(window.ActiveXObject){
			xmlHttp = new ActiveXObject(Microsoft.XMLHTTP);//适用于IE
		}else if(window.XMLHttpRequest){
			xmlHttp = new XMLHttpRequest();//适用于火狐，谷歌等浏览器
		}	
	}
	
	function validUsername(){
		var strFullPath = window.document.location.href;
		var strPath = window.document.location.pathname;
		var pos = strFullPath.indexOf(strPath);
		var prePath = strFullPath.substring(0, pos);
		var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
		var basePath2 = prePath;
		basePath2 = prePath + postPath;
		//以上内容为获取项目根路径的完整代码
		
		//1.创建对象
		createXMLHttpRequest();
		//使用DOM获取username
		var username = document.form.username.value;
		//请求的地址
		var url = basePath2 + "/validusername?username="+username;
		//2.打开异步请求
		xmlHttp.open("GET",url,true);//第一个参数：请求方式 第二个参数：请求地址 第三个参数：表示是否使用异步提交的方式
		
		//3.注册回调函数
		//当状态有变化时，调用回调函数
		xmlHttp.onreadystatechange = callback;
		
		//4.发送请求
		xmlHttp.send(null);
	}
	function callback(){
		if(xmlHttp.readyState == 4){//0:尚未加载  1：打开状态  2：正在发送  3：正在接收 4：接收完毕
			if(xmlHttp.status == 200){
				//成功返回
				//获取相应的内容
				var msg = xmlHttp.responseXML.getElementsByTagName("msg")[0].firstChild.data;
				var flag = xmlHttp.responseXML.getElementsByTagName("flag")[0].firstChild.data;
				setMsg(msg,flag);
			}
		}
	}
	
	function setMsg(msg, flag){
		var userMsg = document.getElementById("un_msg");
		var fontColor = "red";
		if(flag == "true"){
			username_flag=true;
			fontColor = "green";
		}else{
			username_flag=false;
		}
		userMsg.innerHTML = "<font color="+fontColor+" size='3'>"+msg+"</font>";
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
	<div class="page-content">
		<div class="content-nav">人员管理 > 员工注册</div>
		<form name="form" action="<%=basePath%>regist" method="post">
			<fieldset>
				<legend>员工信息</legend>
				<table class="formtable" style="width: 100%;">
					<tr>
						<td width="100px">姓名：</td>
						<td><input type="text" id="accountname" maxlength="20"
							name="employeename" onblur="validEmployeename()" /> <label
							id="ee_msg" class="e_msg"></label></td>
					</tr>
					<tr>
						<td>账户名：</td>
						<td><input type="text" id="accountname" maxlength="20"
							name="username" onblur="validUsername()" /> <label id="un_msg"></label></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" id="password" maxlength="20"
							placeholder="请输入6位以上" name="userpwd" onblur="validUserpwd()" /> <label
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
						<td><input type="text" id="email" maxlength="20" name="email"
							onblur="validEmail()" /> <label id="em_msg" class="e_msg"></label>
						</td>
					</tr>
					<tr align="center">
						<td colspan="3"><label class="msg2">${requestScope.msg}</label></td>
					</tr>
					<tr>
						<td colspan="6" class="command"><input type="button"
							class="clickbutton" value="注册" /> <input type="reset"
							class="clickbutton" value="重置" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>