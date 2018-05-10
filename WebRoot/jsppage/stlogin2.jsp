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
<link rel="stylesheet" href="styles/common.css" />
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
</style>
<script type="text/javascript">
	var user_flag = false;
	var pwd_flag = false;
	var img_flag = false;

	function validUser() {
		var username = document.form.username.value;
		var ckn = /^.*[操|金三胖|你妈的|他妈的].*$/;
		if (ckn.test(username)) {
			document.getElementById("user_err_msg").innerText = "用户名不能包含敏感词";
			document.getElementById("user_err_msg").style.display = "inline";
			user_flag = false;
		} else if (username.length == 0) {
			document.getElementById("user_err_msg").innerText = "用户名不能为空";
			document.getElementById("user_err_msg").style.display = "inline";
			user_flag = false;
		} else {
			document.getElementById("user_err_msg").innerText = "";
			document.getElementById("user_err_msg").style.display = "none";
			user_flag = true;
		}
	}

	function validPwd() {
		var userpwd = document.form.userpwd.value;
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
		var image = document.getElementById("nimage");
		image.src = "../image?" + Math.random();
	}

	var xmlHttp;
	//创建XMLHttpRequest对象
	function createXMLHttpRequest() {
		if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject(Microsoft.XMLHTTP);//适用于IE
		} else if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();//适用于火狐，谷歌等浏览器
		}
	}

	function valiImage() {
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
		var ntoken = document.form.ntoken.value;
		//请求的地址
		var url = basePath2 + "/validimage?ntoken=" + ntoken;
		//2.打开异步请求
		xmlHttp.open("GET", url, true);//第一个参数：请求方式 第二个参数：请求地址 第三个参数：表示是否使用异步提交的方式

		//3.注册回调函数
		//当状态有变化时，调用回调函数
		xmlHttp.onreadystatechange = callback;

		//4.发送请求
		xmlHttp.send(null);
	}
	function callback() {
		if (xmlHttp.readyState == 4) {//0:尚未加载  1：打开状态  2：正在发送  3：正在接收 4：接收完毕
			if (xmlHttp.status == 200) {
				//成功返回
				//获取相应的内容
				var msg = xmlHttp.responseXML.getElementsByTagName("msg")[0].firstChild.data;
				var flag = xmlHttp.responseXML.getElementsByTagName("flag")[0].firstChild.data;
				setMsg(msg, flag);
			}
		}
	}

	function setMsg(msg, flag) {
		var imageMsg = document.getElementById("image_msg");
		var fontColor = "red";
		if (flag == "true") {
			fontColor = "green";
			img_flag = true;
		} else {
			img_flag = false;
		}
		imageMsg.innerHTML = "<font color="+fontColor+" size='3'>" + msg
				+ "</font>";
	}

	window.onload = function() {
		document.form.onsubmit = function() {
			return user_flag && pwd_flag && img_flag;
		}
	}
</script>
</head>
<body>
	<div class="page-content">
		<div class="content-nav">登录</div>
		<form action="<%=basePath%>login" name="form" method="post">
			<fieldset>
				<legend>登录信息</legend>
				<table class="formtable" style="width: 80%;">
					<tr>
						<td width="200px">账号名:</td>
						<td><input id="accountname" name="username" type="text"
							onblur="validUser()" /><label id="user_err_msg" class="err_msg"></label></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input id="new" name="userpwd" type="password"
							onblur="validPwd()" /><label id="pwd_err_msg" class="err_msg"></label></td>
					</tr>
					<tr>
						<td>验证码：<a href="JavaScript:changeImg()"><img id="nimage"
								src="<%=basePath%>image"></a></td>
						<td><input type="text" name="ntoken" onblur="valiImage()"
							style="width: 70px;"><label id="image_msg"></label></td>
					</tr>
					<tr align="center">
						<td colspan="2"><label class="msg2">${requestScope.msg}</label></td>
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
</body>
</html>