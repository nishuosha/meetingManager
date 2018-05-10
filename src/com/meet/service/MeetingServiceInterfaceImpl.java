package com.meet.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meet.base.MeetDate;
import com.meet.base.MeetStatus;
import com.meet.base.Meeting;
import com.meet.base.User;
import com.meet.dao.MeetingDaoInterface;

@Service
public class MeetingServiceInterfaceImpl implements MeetingServiceInterface {
	
	@Autowired
	private MeetingDaoInterface meetingDao;
	

	@Override
	public void saveMeeting(Meeting meeting) {
		meetingDao.saveMeeting(meeting);
	}


	@Override
	public Meeting findMeetingById(Meeting meeting) {
		return meetingDao.findMeetingById(meeting);
	}


	@Override
	public void deleteMeeting(Meeting meeting) {
		meetingDao.deleteMeeting(meeting);
	}


	@Override
	public List<Meeting> getAllMeeting() {
		return meetingDao.getAllMeeting();
	}


	@Override
	public void saveMeetDate(MeetDate meetDate) {
		meetingDao.saveMeetDate(meetDate);
	}


	@Override
	public Meeting getTimeById(Meeting meeting) {
		return meetingDao.getTimeByMeetingId(meeting);
	}


	@Override
	public void saveMeetStatus(MeetStatus meetStatus) {
		meetingDao.saveMeetStatus(meetStatus);
	}


	@Override
	public MeetStatus findMeetingMarked(Meeting meeting) {
		return meetingDao.findMeetingMarked(meeting);
	}

}
