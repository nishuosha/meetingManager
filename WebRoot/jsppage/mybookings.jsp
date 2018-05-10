<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="content-type" content="text/html" ;charset="UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="jsppage/styles/common.css" />
<script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>
<style type="text/css">

</style>
</head>
<body>
	<div class="page-content">
		<div class="content-nav">个人中心 > 我的预定</div>
		<table class="listtable">
			<caption>我预定的会议：</caption>
			<tr class="listheader">
				<th>会议名称</th>
				<th>会议预定时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="meetings_reserve" var="meeting">
			<tr>
				<td><s:property value="#meeting.MeetingName"/> </td>
				<td><s:property value="#meeting.reserationTime"/> </td>
				<td><a class="clickbutton" href="${pageContext.request.contextPath}/meeting/toReserveDetail.action?MeetingID=<s:property value="#meeting.MeetingID" />">查看/撤销</a>
				</td>
			</tr>
			</s:iterator>
		</table>
	</div>
	<br><br><br><br><br><br><br><br><br><br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<hr>
	<img src="${pageContext.request.contextPath}/wechat/getQrcode.action" alt="二维码"  width="100px" height="100px" />
	
</body>
</html>