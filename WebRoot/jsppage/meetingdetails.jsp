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
#divfrom {
	float: left;
	width: 200px;
}

#divto {
	float: left;
	width: 200px;
}

#divoperator {
	float: left;
	width: 50px;
	padding: 60px 5px;
}

#divoperator input[type="button"] {
	margin: 10px 0;
}

#selDepartments {
	display: block;
	width: 100%;
}

#selEmployees {
	display: block;
	width: 100%;
	height: 200px;
}

#selSelectedEmployees {
	display: block;
	width: 100%;
	height: 225px;
}

fieldset {
	width: 800px;
}

.formtable {
	width: 600px;
	padding: 0 auto;
}
</style>
<script type="application/javascript">
        </script>
</head>
<body >
	<div class="page-content">
		<div class="content-nav">会议预定 > 修改会议预定</div>
		<form>
			<fieldset>
				<legend>会议信息</legend>
				<table class="formtable">
					<tr>
						<td>会议名称：</td>
						<td><s:property value="MeetingName"/></td>
					</tr>
					<tr>
						<td>预计参加人数：</td>
						<td><s:property value="participatenum"/></td>
					</tr>
					<tr>
						<td>会议说明：</td>
						<td><textarea id="description" rows="5" readonly><s:property value="Description"/></textarea>
						</td>
					</tr>
					<tr>
						<td>时间段：</td>
					</tr>
					<s:iterator value="meetDates" var="meetdate">
						<tr>
							<td><s:property value="#meetdate.startTime"/> </td>
							<td> --- </td>
							<td><s:property value="#meetdate.endTime"/> </td>
						</tr>
					</s:iterator>
					<tr>
						<td>参会人员：</td>
						<td>
							<table class="listtable">
								<tr class="listheader">
									<th>姓名</th>
									<th>联系电话</th>
									<td>电子邮件</td>
								</tr>
								<s:iterator value="participants">
								<tr>
									<td><s:property value="UserName"/> </td>
									<td><s:property value="phone"/></td>
									<td><s:property value="Email"/></td>
								</tr>
								</s:iterator>
								
							</table>
						</td>
					</tr>
					<tr>
						<td class="command" colspan="2"><input type="button"
							class="clickbutton" value="返回" onclick="window.history.back();" />
						</td>
						<td> <a href="${pageContext.request.contextPath}/meeting/getAllTime.action?MeetingID=<s:property value="MeetingID"/>">标记时间段</a>  </td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>