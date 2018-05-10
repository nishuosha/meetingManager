<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个人信息页面 </title>
	
	<script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>
	
  </head>
  
  <body>
    	
    	<table >
    		<tr>
    			<th>用户名</th>
    			<th>用户姓名</th>
    			<th>邮箱</th>
    			<th>电话</th>
    			<th>绑定状态</th>
    		</tr>
    		<tr>
    			<td><s:property value="UserName"/> </td>
    			<td><s:property value="Name"/> </td>
    			<td><s:property value="Email"/> </td>
    			<td><s:property value="phone"/> </td>
    			<td><s:property value="status"/></td>
    		</tr>
    	</table>
    	
  </body>
</html>
