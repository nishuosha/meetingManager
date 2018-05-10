package com.meet.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
//会议
public class Meeting {

	private int MeetingID;
//	会议名称
	private String MeetingName;
//	预定时间
	private Date ReserationTime;
//	描述
	private String Description;
//	参与人数
	private int participatenum;
//	有效时间
	private int effect;
	
//	预订者
	private User subscriber;
//	参与者
	private Set<User> participants = new HashSet<>();
//	时间段
	private Set<MeetDate> meetDates = new HashSet<MeetDate>();
	
	private Set<MeetStatus> status = new HashSet<MeetStatus>();
	
	
	public void setStatus(Set<MeetStatus> status) {
		this.status = status;
	}
	
	public Set<MeetStatus> getStatus() {
		return status;
	}
	
	
	public void setMeetDates(Set<MeetDate> meetDates) {
		this.meetDates = meetDates;
	}
	
	public Set<MeetDate> getMeetDates() {
		return meetDates;
	}
	
	public int getMeetingID() {
		return MeetingID;
	}
	
	public void setParticipatenum(int participatenum) {
		this.participatenum = participatenum;
	}
	
	public int getParticipatenum() {
		return participatenum;
	}
	

	public void setMeetingID(int meetingID) {
		MeetingID = meetingID;
	}

	public String getMeetingName() {
		return MeetingName;
	}

	public void setMeetingName(String meetingName) {
		MeetingName = meetingName;
	}

	public Date getReserationTime() {
		return ReserationTime;
	}

	public void setReserationTime(Date reserationTime) {
		ReserationTime = reserationTime;
	}


	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}


	public User getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(User subscriber) {
		this.subscriber = subscriber;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<User> participants) {
		this.participants = participants;
	}
	
	public void setEffect(int effect) {
		this.effect = effect;
	}
	
	public int getEffect() {
		return effect;
	}
	
}
