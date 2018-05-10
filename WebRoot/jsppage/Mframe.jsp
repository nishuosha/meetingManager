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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CoolMeeting会议管理系统</title>
</head>

<frameset rows="150,*,93" cols="*" framespacing="0" frameborder="no"
	border="0">

	<frame src="jsppage/top.jsp" name="topFrame" scrolling="No" noresize="noresize"
		id="topFrame" marginwidth="0" marginheight="0" frameborder="0" />

	<frameset cols="200,*" id="frame">
		<frame src="jsppage/left.jsp" name="leftFrame" scrolling="No"
			noresize="noresize" marginwidth="150px" marginheight="0"
			frameborder="0" />
		<frame src="${pageContext.request.contextPath}/user/getNewMeeting.action" name="main" marginwidth="50px"
			marginheight="40px" frameborder="0" scrolling="auto" />
	</frameset>
	<frame src="jsppage/bottom.jsp" name="bottomFrame" scrolling="No"
		noresize="noresize" id="bottomFrame" marginwidth="0" marginheight="0" />

</frameset>
<noframes></noframes>
</html>