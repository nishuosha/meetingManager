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
        var meetingname_flag=false;
        var roomname_flag=false;
        var reservationistid_flag=false;
        var reservefromdate_flag=false;
        var reservetodate_flag=false;
        var meetingfromdate_flag=false;
        var meetingtodate_flag=false;
        
        function validMeetingname(){
        	var meetingname=document.form.meetingname.value;
        	if(meetingname.length==0){
        		document.getElementById("mn_msg").innerText = "会议名称不能为空";
    			document.getElementById("mn_msg").style.display = "inline";
    			meetingname_flag = false;
        	}else{
        		document.getElementById("mn_msg").innerText = "";
    			document.getElementById("mn_msg").style.display = "none";
    			meetingname_flag = true;
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
        
        function validReservationistid(){
        	var reservationistid=document.form.reservationistid.value;
        	if(reservationistid.length==0){
        		document.getElementById("rvn_msg").innerText = "预订者姓名不能为空";
    			document.getElementById("rvn_msg").style.display = "inline";
    			reservationistid_flag=false;
        	}else{
        		document.getElementById("rvn_msg").innerText = "";
    			document.getElementById("rvn_msg").style.display = "none";
    			reservationistid_flag = true;
        	}
        }
        
        function validReservefromdate(){
        	var reservefromdate=document.form.reservefromdate.value;
        	if(reservefromdate.length==0){
        		document.getElementById("rf_msg").innerText = "预订日期不能为空";
    			document.getElementById("rf_msg").style.display = "inline";
    			reservefromdate=false;
        	}else{
        		document.getElementById("rf_msg").innerText = "";
    			document.getElementById("rf_msg").style.display = "none";
    			reservefromdate = true;
        	}
        }
        function validReservetodate(){
        	var reservetodate=document.form.reservetodate.value;
        	if(reservetodate.length==0){
        		document.getElementById("rt_msg").innerText = "预订日期不能为空";
    			document.getElementById("rt_msg").style.display = "inline";
    			reservetodate_flag=false;
        	}else{
        		document.getElementById("rt_msg").innerText = "";
    			document.getElementById("rt_msg").style.display = "none";
    			reservetodate_flag = true;
        	}
        }
        function validMeetingfromdate(){
        	var meetingfromdate=document.form.meetingfromdate.value;
        	if(meetingfromdate.length==0){
        		document.getElementById("mf_msg").innerText = "会议日期不能为空";
    			document.getElementById("mf_msg").style.display = "inline";
    			meetingfromdate_flag=false;
        	}else{
        		document.getElementById("mf_msg").innerText = "";
    			document.getElementById("mf_msg").style.display = "none";
    			meetingfromdate_flag = true;
        	}
        }
        function validMeetingtodate(){
        	var meetingtodate=document.form.meetingtodate.value;
        	if(meetingtodate.length==0){
        		document.getElementById("mt_msg").innerText = "会议日期不能为空";
    			document.getElementById("mt_msg").style.display = "inline";
    			meetingtodate_flag=false;
        	}else{
        		document.getElementById("mt_msg").innerText = "";
    			document.getElementById("mt_msg").style.display = "none";
    			meetingtodate_flag = true;
        	}
        }
        
        window.onload = function() {
    		document.form.onsubmit = function() {
    			return  meetingname_flag && roomname_flag && reservationistid_flag && reservefromdate_flag && reservetodate_flag && meetingfromdate_flag && meetingtodate_flag;
    		}
    	}
        
       </script>
</head>
<body>

	<div class="page-content">
		<div class="content-nav">会议预定 > 搜索会议</div>
		<form name="form">
			<fieldset>
				<legend>搜索会议</legend>
				<table class="formtable">
					<tr>
						<td>会议名称：</td>
						<td><input type="text" id="meetingname" maxlength="20"
							name="meetingname" onblur="validMeetingname()" /> <label
							class="e_msg" id="mn_msg"></label></td>
						<td>会议室名称：</td>
						<td><input type="text" id="roomname" maxlength="20"
							name="roomname" onblur="validRoomname()" /> <label class="e_msg"
							id="rn_msg"></label></td>
						<td>预定者姓名：</td>
						<td><input type="text" id="reservername" maxlength="20"
							name="reservationistid" onblur="validReservationistid()" /> <label
							class="e_msg" id="rvn_msg"></label></td>
					</tr>
					<tr>
						<td>预定日期：</td>
						<td colspan="5">从&nbsp;<input type="date"
							id="reservefromdate" placeholder="例如：2013-10-20"
							name="reservefromdate" onblur="validReservefromdate()" /><label
							class="e_msg" id="rf_msg"></label> 到&nbsp;<input type="date"
							id="reservetodate" placeholder="例如：2013-10-22"
							name="reservetodate" onblur="validReservetodate()" /><label
							class="e_msg" id="rt_msg"></label>
						</td>
					</tr>
					<tr>
						<td>会议日期：</td>
						<td colspan="5">从&nbsp;<input type="date"
							id="meetingfromdate" placeholder="例如：2013-10-20"
							name="meetingfromdate" onblur="validMeetingfromdate()" /><label
							class="e_msg" id="mf_msg"></label> 到&nbsp;<input type="date"
							id="meetingtodate" placeholder="例如：2013-10-22"
							name="meetingtodate" onblur="validMeetingtodate()" /><label
							class="e_msg" id="mt_msg"></label>
						</td>
					</tr>
					<tr>
						<td colspan="6" class="command"><input type="button"
							class="clickbutton" value="查询" /> <input type="reset"
							class="clickbutton" value="重置" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
		<div>
			<h3 style="text-align: center; color: black">查询结果</h3>
			<div class="pager-header">
				<div class="header-info">
					共<span class="info-number">${size }</span>条结果， 分成<span
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
				<th>会议名称</th>
				<th>会议室名称</th>
				<th>会议开始时间</th>
				<th>会议结束时间</th>
				<th>会议预定时间</th>
				<th>预定者</th>
				<th>操作</th>
			</tr>
			<s:iterator>
			<tr>
				<td><s:property value="MeetingName"/> </td>
				<td>第一会议室</td>
				<td><s:property value="StartTime"/></td>
				<td><s:property value="EndTime"/></td>
				<td>2013-10-10 16:00</td>
				<td><s:property value="subscriber.Name"/></td>
				<td><a class="clickbutton" href="${pageContext.request.contextPath}/meeting/toParticipateDetail.action?MeetingID=<s:property value="MeetingID" />">查看详情</a></td>
			</tr>
			</s:iterator>
		</table>
	</div>

</body>
</html>