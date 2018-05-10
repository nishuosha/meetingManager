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
<meta http-equiv="content-type" content="text/html" ;charset="UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="jsppage/styles/common.css" />
<style type="text/css">
fieldset {
	width: 770px;
}

.formtable {
	width: 800px;
	padding: 0 auto;
}
</style>
</head>
<body>

	<div class="page-content">
		<div class="content-nav">会议预定 > 搜索员工</div>
		<form action="${pageContext.request.contextPath}/user/searchUser.action" method="post">
			<fieldset>
				<legend>搜索会议</legend>
				<table class="formtable">
					<tr>
						<td>姓名：</td>
						<td><input type="text" id="Name" name="Name" maxlength="20" /></td>
						<td>账号名：</td>
						<td><input type="text" id="UserName" name="UserName" maxlength="20" /></td>
					</tr>
					<tr>
						<td colspan="6" class="command"><input type="submit"
							class="clickbutton" value="查询" /> <input type="reset"
							class="clickbutton" value="重置" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
		<br />
		<div>
			<h3 style="text-align: center; color: black">查询结果</h3>
			<div class="pager-header">
				<div class="header-info">
					共<span class="info-number">${size} </span>条结果， 分成<span
						class="info-number">6</span>页显示， 当前第<span class="info-number">1</span>页
				</div>
				<div class="header-nav">
					<input type="button" class="clickbutton" value="首页" /> <input
						type="button" class="clickbutton" value="上页" /> <input
						type="button" class="clickbutton" value="下页" /> <input
						type="button" class="clickbutton" value="末页" /> 跳到第<input
						type="text" id="pagenum" class="nav-number" />页 <input
						type="button" class="clickbutton" value="跳转" />
				</div>
			</div>
		</div>
		<table class="listtable">
			<tr class="listheader">
				<th>姓名</th>
				<th>账号名</th>
				<th>联系电话</th>
				<th>电子邮件</th>
				<th>操作</th>
			</tr>
			
			<s:iterator>
				<tr>
					<td><s:property value="Name"/> <td>
					<td><s:property value="UserName"/><td>
					<td><s:property value="phone"/><td>
					<td><s:property value="Email"/><td>
					<td><a href="#">操作</a> </td>
				</tr>
			</s:iterator>	
			
		</table>
	</div>
</body>
</html>