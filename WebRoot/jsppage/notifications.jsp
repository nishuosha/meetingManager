<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
</head>
<div class="page-content">
	<div class="content-nav">个人中心 > 最新通知</div>
	<table class="listtable">
		<caption>未来7天我要参加的会议:</caption>
		<tr class="listheader">
			<th style="width: 300px">会议名称</th>
			<th>会议室</th>
			<th>起始时间</th>
			<th>结束时间</th>
			<th style="width: 100px">操作</th>
		</tr>
		
		<s:iterator value="meetings_participate" var="meeting">
		<tr>
			<td><s:property value="#meeting.MeetingName"/> </td>
			<td>第一会议室</td>
			<td><s:property value="#meeting.StartTime"/></td>
			<td><s:property value="#meeting.EndTime"/></td>
			<td><a class="clickbutton" href="${pageContext.request.contextPath}/meeting/toParticipateDetail.action?MeetingID=<s:property value="#meeting.MeetingID" />">查看详情</a></td>
		</tr>
		</s:iterator>
	</table>
	<table class="listtable">
		<caption>已取消的会议:</caption>
		<tr class="listheader">
			<th style="width: 300px">会议名称</th>
			<th>会议室</th>
			<th>起始时间</th>
			<th>结束时间</th>
			<th>取消原因</th>
			<th style="width: 100px">操作</th>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><a class="clickbutton" href="meetingdetails.jsp">查看详情</a></td>
		</tr>
	</table>
</div>
</body>
</html>