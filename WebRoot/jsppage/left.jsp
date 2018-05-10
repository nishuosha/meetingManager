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
<link rel="stylesheet" type="text/css" href="jsppage/styles/common.css" />
<style type="text/css">
</style>
</head>

<body>
	<div class="page-sidebar">
		<div class="sidebar-menugroup">
			<div class="sidebar-grouptitle">个人中心</div>
			<ul class="sidebar-menu">
				<li class="sidebar-menuitem"><a href="${pageContext.request.contextPath}/user/getNewMeeting.action"
					target="main">最新通知</a></li>
				<li class="sidebar-menuitem active"><a href="${pageContext.request.contextPath}/user/getMyReserve.action"
					target="main">我的预定</a></li>
				<li class="sidebar-menuitem"><a href="${pageContext.request.contextPath}/user/getMyParticipate.action"
					target="main">我的会议</a></li>
			</ul>
		</div>
		<div class="sidebar-menugroup">
			<div class="sidebar-grouptitle">人员管理</div>
			<ul class="sidebar-menu">
				<li class="sidebar-menuitem"><a href="${pageContext.request.contextPath}/user/getAllUser.action"
					target="main">搜索员工</a></li>
			</ul>
		</div>
		<div class="sidebar-menugroup">
			<div class="sidebar-grouptitle">会议预定</div>
			<ul class="sidebar-menu">
				<li class="sidebar-menuitem"><a href="${pageContext.request.contextPath}/meeting/toBookMeeting.action"
					target="main">预定会议</a></li>
				<li class="sidebar-menuitem"><a href="${pageContext.request.contextPath}/meeting/toSearchMeeting.action"
					target="main">搜索会议</a></li>
			</ul>
		</div>
	</div>

</body>
</html>