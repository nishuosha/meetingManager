<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="jsppage/styles/common.css" />

<script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>

<script type="text/javascript">
	
	
	
</script>
</head>

<body>
	<div class="page-header">
		<div class="header-banner">
			<img src="jsppage/images/header.png" alt="CoolMeeting" />
		</div>
		<div class="header-title">欢迎访问Cool-Meeting会议管理系统</div>
		<div class="header-quicklink">
			欢迎您，<strong>
			<a href="${pageContext.request.contextPath}/user/userInfo.action?UserID=${user.userID}">${user.name}</a>
			</strong> <a href="jsppage/changepassword.jsp" target="main">[修改密码]</a>
		</div>
	</div>
</body>
</html>