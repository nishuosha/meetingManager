package com.meet.dao;

import java.util.List;
import java.util.Set;

import com.meet.base.MeetDate;
import com.meet.base.MeetStatus;
import com.meet.base.Meeting;
import com.meet.base.User;

public interface MeetingDaoInterface {

//	添加会议
	public void saveMeeting(Meeting meeting);
//	按照id查找会议
	public Meeting findMeetingById(Meeting meeting);
//	删除会议
	public void deleteMeeting(Meeting meeting);
//	获取所有会议
	public List<Meeting> getAllMeeting();
//	保存标记的时间段
	public void saveMeetDate(MeetDate meetDate);
//	获取某个会议对应的时间段
	public Meeting getTimeByMeetingId(Meeting meeting);
	
	public void saveMeetStatus(MeetStatus meetStatus);
	
	public MeetStatus findMeetingMarked(Meeting meeting);
}
