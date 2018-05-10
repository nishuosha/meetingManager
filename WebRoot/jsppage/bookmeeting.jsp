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
	width: 150px;
}

#divto {
	float: left;
	width: 150px;
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
	width: 770px;
}

.formtable {
	width: 800px;
	padding: 0 auto;
}

.e_msg {
	color: red;
	font-size: 15px;
	display: none;
}
</style>
<script type="text/javascript" src="jsppage/js/jquery-3.1.1.js"></script>
<script type="application/javascript">
			            
	$(function() {
		var i = 1;
		
		$("#addUser").click(function() {
			$("#selSelectedEmployees").append($("#selEmployees option:selected:first"));
			$("#selSelectedEmployees option").attr("selected","selected");
		});
		
		$("#removeUser").click(function() {
			$("#selEmployees").append($("#selSelectedEmployees option:selected:first"));
		});
		
		$(".addTime").click(function(){
			var time = $(".time:first").clone(true).attr("id", i);
			i = i + 1;
			$(".formtable").append(time);
		});
		
		$("#effect").blur(function() {
			var effect = $("#effect").val();
			if(isNaN(effect)) {
				alert("请输入一个有效数字");
			}
		});
		
		var flag = false;
		
		
		
	$(".test").click(function() {
		var data = "";
		for( var x = 0 ; x < i ; x ++) {
			var startdate = $("tr[id='" + x + "'] #startdate").val();		
			var enddate = $("tr[id='" + x + "'] #enddate").val();
			var starttime = $("tr[id='" + x + "'] #starttime").val();
			var endtime = $("tr[id='" + x + "'] #endtime").val();
			if(x == 0) {
				data = data + "startdate=" + startdate + "&starttime=" + starttime + "&enddate=" + enddate + "&endtime=" + endtime;
			} else {
				data = data + "&startdate=" + startdate + "&starttime=" + starttime + "&enddate=" + enddate + "&endtime=" + endtime;
			}
		}
		
		var url = "${pageContext.request.contextPath}/meeting/checkTime.action";
		$.ajax({
			type:"post",
			url:url,
			data:data,
			success:function(result) {
				var a = result.length;
				var cishu = 0;
				
				for(var y = 0 ; y < a ; y ++) {
					if(result[y] == '0') {
						$("tr[id='" + y + "'] .error").text("此时间段不可用，请重新选择");
					} else {
						$("tr[id='" + y + "'] .error").text("时间段可用");
						cishu ++;
					}
				}
				
				if(cishu == a) {
					$("tr[class='time'] .error").attr("color","green").text("时间段可用");
					$("#tijiao").removeAttr("disabled");
				}
				
			}
			
		});
		
	});
	
	})
	
	
	
	
	            
</script>
<script type="text/javascript">
        
        function validMeetingname(){
        	var meetingname=document.form.meetingname.value;
        	if( meetingname.length==0){
        		document.getElementById("mn_msg").innerText = "会议名称不能为空";
    			document.getElementById("mn_msg").style.display = "inline";
        	}else{
        		document.getElementById("mn_msg").innerText = "";
    			document.getElementById("mn_msg").style.display = "none";
        	}
        }
  
        </script>
</head>
<body>
<div class="page-content">
	<div class="content-nav">会议预定 > 预定会议</div>
	<form name="form" action="${pageContext.request.contextPath}/meeting/addMeeting.action" method="post">
		<fieldset>
			<legend>会议信息</legend>
			<table class="formtable">
				<tr>
					<td>会议名称：</td>
					<td><input type="text" id="meetingname" maxlength="20"
						name="MeetingName"  /> <label
						id="mn_msg" class="e_msg"></label></td>
				</tr>
				<tr>
					<td>预计参加人数：</td>
					<td><input type="text" id="numofattendents"
						name="participatenum"  /> <label
						id="pp_msg" class="e_msg"></label></td>
				</tr>
				<tr class="time" id="0">
					<td>预计时间：</td>
					<td><input type="date" id="startdate" name="startdate"
						 /><label id="bd_msg" class="e_msg"></label>
						<input type="time" id="starttime" name="starttime"
						 /><label id="bdt_msg" class="e_msg"></label>
					</td>
					<td>
						<input type="date" id="enddate" name="enddate"
						 /><label id="bd_msg" class="e_msg"></label>
						<input type="time" id="endtime" name="endtime"
						 /><label id="edt_msg" class="e_msg"></label>
					</td>
					<td><a class="addTime">添加</a></td>
					<td><font class="error" color="red"></font></td>
				</tr>
					
				<tr>
					<td>有效时间：</td>
					<td><input type="text" name="effect" id="effect"></td>
				</tr>
				<tr>
					<td>会议说明：</td>
					<td><textarea id="description" rows="5" name="Description"></textarea></td>
				</tr>
				<tr>
					<td>选择参会人员：</td>
					<td>
						<div id="divfrom">
<!-- 							<select id="selDepartments" onchange="fillEmployees()"></select>  -->
							<select id="selEmployees" multiple="true">
								<s:iterator>
									<option value='<s:property value="UserID"/>'><s:property value="UserName"/> </option>
								</s:iterator>
							</select>
						</div>
						<div id="divoperator">
							<input type="button" class="clickbutton" id="addUser" value="&gt;" /> 
							<input type="button" class="clickbutton" id="removeUser" value="&lt;"/>
						</div>
						<div id="divto">
							<select id="selSelectedEmployees" multiple="true" name="ids">
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td class="command" colspan="2">
						
					<input type="submit"
						class="clickbutton" id="tijiao" value="预定会议"  disabled="disabled" /> <input type="reset"
						class="clickbutton" value="重置" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<button class="test" name="test" value="检测时间">检测时间是否可用</button>
</div>
</body>
</html>