<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微信登录</title>
    <script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>
    <script type="text/javascript">
		var t1;
		var url = "${pageContext.request.contextPath}/user/checkLogin.action";
		
		$(function() {
			t1 = window.setInterval("login()",5000);
		});
		
		function login() {
			$.ajax({
				url:url,
				type:'post',
				success:function(result) {
					if(result == 1) {
						window.clearInterval(t1);
						window.location.href = "jsppage/Mframe.jsp";
					}
				}		
			});
		}
				    	
    </script>
  </head>
  
  
  
  <body>
    	<strong>扫描下方二维码即可实现登录</strong>
    	<br>
    	<img alt="二维码" src="${pageContext.request.contextPath}/codeServlet">
    	
    	
    	
  </body>
</html>
