package com.meet.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//时间段
public class MeetDate {

	private int id;
//	开始时间
	private Date startTime; 
//	结束时间
	private Date endTime;
//	对应的会议
	private Meeting meeting;
//	对应的用户
	private Set<User> users = new HashSet<User>();

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	@Override
	public boolean equals(Object obj) {
		MeetDate m = null;
		if(obj instanceof MeetDate) {
			m = (MeetDate) obj;
			if(this.id == m.getId()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id * 1024;
	}
	
}
