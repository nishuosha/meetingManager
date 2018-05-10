package com.meet.dao;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.meet.base.MeetDate;
import com.meet.base.MeetStatus;
import com.meet.base.Meeting;
import com.meet.base.User;

public class MeetingDaoInterfaceImpl extends HibernateDaoSupport implements MeetingDaoInterface {
	
	@Override
	public void saveMeeting(Meeting meeting) {
		this.getHibernateTemplate().saveOrUpdate(meeting);
	}

	@Override
	public Meeting findMeetingById(Meeting meeting) {
		return this.getHibernateTemplate().get(Meeting.class, meeting.getMeetingID());
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		this.getHibernateTemplate().delete(meeting);
	}

	@Override
	public List<Meeting> getAllMeeting() {
		return (List<Meeting>) this.getHibernateTemplate().find("from Meeting");
	}

	@Override
	public void saveMeetDate(MeetDate meetDate) {
		this.getHibernateTemplate().save(meetDate);
	}

	@Override
	public Meeting getTimeByMeetingId(Meeting meeting) {
		return this.getHibernateTemplate().get(Meeting.class, meeting.getMeetingID());
	}

	@Override
	public void saveMeetStatus(MeetStatus meetStatus) {
		this.getHibernateTemplate().save(meetStatus);
	}

	@Override
	public MeetStatus findMeetingMarked(Meeting meeting) {
		List<MeetStatus> ms = (List<MeetStatus>) this.getHibernateTemplate().find("from MeetStatus m where m.meeting = ? and m.status = 1", meeting);
		return ms.get(0);
	}

}
