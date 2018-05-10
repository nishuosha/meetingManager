<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html" ;charset="UTF-8">
<title>CoolMeeting会议管理系统</title>
<link rel="stylesheet" href="styles/common.css" />
<style type="text/css">
fieldset {
	width: 770px;
}

.formtable {
	width: 880px;
	padding: 0 auto;
}

.e_msg {
	color: red;
	font-size: 15px;
	display: none;
}
</style>
<script type="text/javascript">
        var roomcode_flag=false;
        var roomname_flag=false;
        var capacity_flag=false;
       
       
        
        function validRoomcode(){
        	var roomcode=document.form.roomcode.value;
        	if(roomcode.length==0){
        		document.getElementById("rc_msg").innerText = "门牌号不能为空";
    			document.getElementById("rc_msg").style.display = "inline";
    			roomcode_flag = false;
        	}else{
        		document.getElementById("rc_msg").innerText = "";
    			document.getElementById("rc_msg").style.display = "none";
    			roomcode_flag = true;
        	}
        }
         
        function validRoomname(){
        	var roomname=document.form.roomname.value;
        	if(roomname.length==0){
        		document.getElementById("rn_msg").innerText = "会议室名称不能为空";
    			document.getElementById("rn_msg").style.display = "inline";
    			roomname_flag=false;
        	}else{
        		document.getElementById("rn_msg").innerText = "";
    			document.getElementById("rn_msg").style.display = "none";
    			roomname_flag = true;
        	}
        }
        
        function validCapacity(){
        	var capacity=document.form.capacity.value;
        	if(capacity.length==0){
        		document.getElementById("cc_msg").innerText = "最多容纳人数不能为空";
    			document.getElementById("cc_msg").style.display = "inline";
    			capacity_flag=false;
        	}else{
        		document.getElementById("cc_msg").innerText = "";
    			document.getElementById("cc_msg").style.display = "none";
    			capacity_flag = true;
        	}
        }
        
      
        
       
        
        window.onload = function() {
    		document.form.onsubmit = function() {
    			return roomcode_flag && roomname_flag && capacity_flag ;
    		}
    	}
        </script>
</head>
<body>
	<div class="page-content">
		<div class="content-nav">会议预定 > 修改会议室信息</div>
		<form name="form">
			<fieldset>
				<legend>会议室信息</legend>
				<table class="formtable">
					<tr>
						<td>门牌号:</td>
						<td><input id="roomnumber" name="roomcode" type="text"
							value="201" maxlength="10" onblur="validRoomcode()" /> <label
							id="rc_msg" class="e_msg"></label></td>
					</tr>
					<tr>
						<td>会议室名称:</td>
						<td><input id="roomname" name="roomname" type="text"
							value="第一会议室" maxlength="20" onblur="validRoomname()" /> <label
							id="rn_msg" class="e_msg"></label></td>
					</tr>
					<tr>
						<td>最多容纳人数：</td>
						<td><input id="roomcapacity" name="capacity" type="text"
							value="15" onblur="validCapacity()" /> <label id="cc_msg"
							class="e_msg"></label></td>
					</tr>
					<tr>
						<td>当前状态：</td>
						<td><input type="radio" id="status" name="status"
							checked="checked" value="1" /><label for="status">启用</label> <input
							type="radio" id="status" name="status" /><label for="status"
							value="0">停用</label> <input type="radio" id="status"
							name="status" /><label for="status" value="-1">删除</label></td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><textarea id="description" maxlength="200" rows="5"
								cols="60">本会议室配备了投影、幕布、音响设备。</textarea></td>
					</tr>
					<tr>
						<td colspan="2" class="command"><input type="submit"
							value="确认修改" class="clickbutton" /> <input type="button"
							class="clickbutton" value="返回" onclick="window.history.back();" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	</div>
</body>
</html>