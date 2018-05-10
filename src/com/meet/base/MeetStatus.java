package com.meet.base;

import java.util.HashSet;
import java.util.Set;

public class MeetStatus {
	
	private int id;
	private int status;
	
	private Meeting meeting;
	
	private Set<User> users = new HashSet<User>();
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	
	
}
