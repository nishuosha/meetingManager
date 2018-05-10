package com.meet.service;

import java.util.List;
import java.util.Set;

import com.meet.base.MeetDate;
import com.meet.base.MeetStatus;
import com.meet.base.Meeting;
import com.meet.base.User;

public interface MeetingServiceInterface {

	public void saveMeeting(Meeting meeting);
	
	public Meeting findMeetingById(Meeting meeting);
	
	public void deleteMeeting(Meeting meeting);
	
	public List<Meeting> getAllMeeting();
	
	public void saveMeetDate(MeetDate meetDate);
	
	public Meeting getTimeById(Meeting meeting);
	
	public void saveMeetStatus(MeetStatus meetStatus);
	
	public MeetStatus findMeetingMarked(Meeting meeting);
}
