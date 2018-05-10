<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="content-type" content="text/html" charset="UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="jsppage/styles/common.css" />
<style type="text/css">
</style>
</head>
<body>
	<div class="page-content">
		<div class="content-nav">个人中心 > 我的会议</div>
		<table class="listtable">
			<caption>我将参加的会议：</caption>
			<tr class="listheader">
				<th>会议名称</th>
				<th>会议预定时间</th>
				<th>预定者</th>
				<th>操作</th>
			</tr>
			<s:iterator value="meetings_participate" var="meeting">
			<tr>
				<td><s:property value="#meeting.MeetingName"/> </td>
				<td><s:property value="#meeting.reserationTime"/></td>
				<td><s:property value="#meeting.subscriber.name"/></td>
				<td><a class="clickbutton" href="${pageContext.request.contextPath}/meeting/toParticipateDetail.action?MeetingID=<s:property value="#meeting.MeetingID" />">查看详情</a></td>
			</tr>
			</s:iterator>
		</table>
	</div>
</body>
</html>