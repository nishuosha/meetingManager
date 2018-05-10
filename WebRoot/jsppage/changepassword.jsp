<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
.e_msg {
	color: red;
	font-size: 15px;
	display: none;
}
</style>
<script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>
<script type="text/javascript">
        var originpwd_flag=false;
        var newpwd_flag=false;
        var confirm_flag=false;
        
        function validOriginpwd(){
        	var originpwd= $("#origin").val();
        	if(originpwd.length==0){
        		document.getElementById("op_msg").innerText = "原密码不能为空";
    			document.getElementById("op_msg").style.display = "inline";
    			originpwd_flag = false;
        	}else{
        	
        		var url = "${pageContext.request.contextPath}/user/valiPwd.action";
        		var data = "pwd=" + $("#origin").val();
        		$.ajax({
        			url:url,
        			data:data,
        			type:"post",
        			success:function(result) {
        				if(result == 1) {
        					document.getElementById("op_msg").innerText = "";
    						document.getElementById("op_msg").style.display = "none";
    						originpwd_flag = true;
        				} 
        				if(result == 0) {
        					document.getElementById("op_msg").innerText = "原密码不正确";
    						document.getElementById("op_msg").style.display = "inline";
    						originpwd_flag = false;
        				}
        			}
        			
        		});
        		
        		
        	}
        }
        function validNewpwd(){
        	var newpwd=document.form.newpwd.value;
        	if(newpwd.length==0){
        		document.getElementById("np_msg").innerText = "新密码不能为空";
    			document.getElementById("np_msg").style.display = "inline";
    			newpwd_flag = false;
        	}else{
        		document.getElementById("np_msg").innerText = "";
    			document.getElementById("np_msg").style.display = "none";
    			newpwd_flag = true;
        	}
        }
        
        function validConfirmpwd(){
        	var confirmpwd=document.form.confirmpwd.value;
        	if(confirmpwd.length==0){
        		document.getElementById("cf_msg").innerText = "确认新密码不能为空";
    			document.getElementById("cf_msg").style.display = "inline";
    			confirmpwd_flag = false;
        	}else{
        	
        		var pwd2 = $("#new").val();
        		if(confirmpwd != pwd2) {
        			document.getElementById("cf_msg").innerText = "两次密码不一致";
    				document.getElementById("cf_msg").style.display = "inline";
    				confirmpwd_flag = false;
        		} else {
        		document.getElementById("cf_msg").innerText = "";
    			document.getElementById("cf_msg").style.display = "none";
    			confirmpwd_flag = true;
        		}
        	}
        }
        window.onload = function() {
    		document.form.onsubmit = function() {
    			return origin_flag &&  newpwd_flag && confirmpwd_flag;
    		}
    	}
        </script>
</head>
<body>
	<div class="page-content">
		<div class="content-nav">修改密码</div>
		<form name="form" action="${pageContext.request.contextPath}/user/changePwd.action" method="post">
			<fieldset>
				<legend>修改密码信息</legend>
				<table class="formtable" style="width: 50%">
					<tr>
						<td>原密码:</td>
						<td><input id="origin" type="password" name="pwd"
							onblur="validOriginpwd()" /> <label id="op_msg" class="e_msg"></label>
						</td>
					</tr>
					<tr>
						<td>新密码:</td>
						<td><input id="new" type="password" name="newpwd"
							onblur="validNewpwd()" /> <label id="np_msg" class="e_msg"></label>
						</td>
					</tr>
					<tr>
						<td>确认新密码：</td>
						<td><input id="confirm" type="password" name="confirmpwd"
							onblur="validConfirmpwd()" /> <label id="cf_msg" class="e_msg"></label>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="command"><input type="submit"
							value="确认修改" class="clickbutton" /> <input type="button"
							value="返回" class="clickbutton" onclick="window.history.back();" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>