package com.meet.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JInternalFrame;
//用户
public class User {

	private int UserID;
//	用户名
	private String UserName;
//	密码
	private String UserPwd;
//	姓名
	private String Name;
//	邮箱
	private String email;
//	电话
	private String phone;
//	状态，是否已绑定微信
	private int status;
	private Date DecisionTime;
	private boolean invitation;
	private boolean Sign;
//	绑定微信后对应的微信方面的id
	private String openid;
//	登录标志位
	private int login;
	
	public void setLogin(int login) {
		this.login = login;
	}
	
	public int getLogin() {
		return login;
	}
	
	private Set<MeetStatus> meetStatus = new HashSet<MeetStatus>();
	
	public void setMeetStatus(Set<MeetStatus> meetStatus) {
		this.meetStatus = meetStatus;
	}
	
	public Set<MeetStatus> getMeetStatus() {
		return meetStatus;
	}
	
	private Set<MeetDate> dates = new HashSet<MeetDate>();
	
	private Set<Meeting> meeting_reserve = new HashSet<>(); //预定的会议
	private Set<Meeting> meeting_participate = new HashSet<>(); //参与的会议
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserPwd() {
		return UserPwd;
	}
	public void setUserPwd(String userPwd) {
		UserPwd = userPwd;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDecisionTime() {
		return DecisionTime;
	}
	public void setDecisionTime(Date decisionTime) {
		DecisionTime = decisionTime;
	}
	public boolean isInvitation() {
		return invitation;
	}
	public void setInvitation(boolean invitation) {
		this.invitation = invitation;
	}
	public boolean isSign() {
		return Sign;
	}
	public void setSign(boolean sign) {
		Sign = sign;
	}
	public Set<Meeting> getMeeting_reserve() {
		return meeting_reserve;
	}
	public void setMeeting_reserve(Set<Meeting> meeting_reserve) {
		this.meeting_reserve = meeting_reserve;
	}
	public Set<Meeting> getMeeting_participate() {
		return meeting_participate;
	}
	public void setMeeting_participate(Set<Meeting> meeting_participate) {
		this.meeting_participate = meeting_participate;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOpenid() {
		return openid;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		User user = null;
//		if(obj instanceof User) {
//			user = (User) obj;
//			int a = this.UserID;
//			if(this.UserID == user.getUserID()) {
//				return true;
//			}
//		} 
//		
//		return false;
//	}
	
	public void setDates(Set<MeetDate> dates) {
		this.dates = dates;
	}
	
	public Set<MeetDate> getDates() {
		return dates;
	}
}
