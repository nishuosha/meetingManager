<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    

  </head>
  
  <body>
    	<form action="${pageContext.request.contextPath}/user/markTime.action" method="post">
    		
    		
    		<s:iterator>
    			<input type="checkbox" name="ids" value='<s:property value="id" />' />
    			<s:property value="startTime" /> -- <s:property value="endTime" />
    			<br/>	
    			
    		</s:iterator>
    		
    		<input type="hidden"  name="meetid" value='<s:property value="#meetid"/>' />
    		<input type="submit" value="确定">
    	</form>
  </body>
</html>
