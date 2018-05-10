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
<base href="<%=basePath%>">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="jsppage/styles/common.css" />
<style type="text/css">
.ch_div {
	width: 200px;
	height: 100px;
	margin: 0 auto;
	text-align: center;
}
</style>
</head>
<body>
	
	<div class="page-header">
		<div class="header-banner">
			<img src="jsppage/images/header.png" alt="CoolMeeting" />
		</div>
		<div class="header-title">欢迎访问Cool-Meeting会议管理系统</div>
	</div>
	<div class="ch_div">
		<br>
		<button style="align: center; width: 100px; height: 30px"
			onclick="window.location.href='jsppage/stlogin.jsp'">登录</button>
		<br>
		<a href="jsppage/qrcode.jsp">如果您已绑定微信，点击可以微信登录</a>
		<br>
		<button style="align: center; width: 100px; height: 30px"
			onclick="window.location.href='jsppage/stregist.jsp'">注册</button>
	</div>
	<div class="page-footer">
		<hr />
		更多问题，欢迎联系<a href="mailto:webmaster@eeg.com">管理员</a> <img
			src="jsppage/images/footer.png" alt="CoolMeeting" />
	</div>
</body>
</html>