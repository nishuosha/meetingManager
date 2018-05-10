package com.meet.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.meet.base.Meeting;
import com.meet.base.User;

public class UnitTest {

	@Test
	public void test() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 1);
		
		Meeting meeting = new Meeting();
		meeting.setMeetingName("456");
		session.save(meeting);

		Meeting meeting2 = new Meeting();
		meeting2.setMeetingName("678");
		
		session.save(meeting2);
		
		Set<Meeting> meetings = new HashSet<>();
		meetings.add(meeting);
		meetings.add(meeting2);
		
		user.setMeeting_participate(meetings);
		
		transaction.commit();
	}
	
	@Test
	public void test2() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Meeting meeting4 = session.get(Meeting.class, 4);
		Meeting meeting5 = session.get(Meeting.class, 5);
		
		User u1 = session.get(User.class, 1);
		
		Set<Meeting> meetings = new HashSet<>();
		meetings.add(meeting4);
		meetings.add(meeting5);
		
		u1.setMeeting_reserve(meetings);
		
		transaction.commit();
	}
	
	@Test
	public void test3() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Meeting meeting4 = session.get(Meeting.class, 5);
		
		User u1 = session.get(User.class, 1);
		
		User u2 = session.get(User.class, 2);
		
		
		
		
		transaction.commit();
	}
	
	@Test
	public void test4() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		User u1 = session.get(User.class, 3);
		
		Meeting m5 = session.get(Meeting.class, 5);
		
		u1.getMeeting_reserve().add(m5);
		
		transaction.commit();
		
	}
	
	@Test
	public void test5() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 1);
		
		Set<Meeting> meetings = user.getMeeting_reserve();
		
		System.out.println(meetings.size());
		transaction.commit();
	}
	
	@Test
	public void test6() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 1);
		
		Set<Meeting> meetings = user.getMeeting_participate();
		
		System.out.println(meetings.size());
		
		transaction.commit();
		
	}
	
	@Test
	public void test7() {
		Set<User> users = new HashSet<User>();
		
		User u1 = new User();
		u1.setUserID(1);
		
		User u2 = new User();
		u2.setUserID(2);
		
		User u3 = new User();
		u3.setUserID(3);
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
		
		User u = new User();
		u.setUserID(1);
		
		System.out.println(u1.equals(u));
		
		System.out.println();
	}
}
