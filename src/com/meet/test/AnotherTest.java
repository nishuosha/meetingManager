package com.meet.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.compare.CalendarComparator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.meet.base.Meeting;
import com.meet.base.User;
import com.meet.service.UserServiceInterface;
import com.meet.util.TokenAccess;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class AnotherTest {

	@Test
	public void test1() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 1);
		
		Meeting m = new Meeting();
			
		m.setMeetingName("123");
		
		
		user.getMeeting_reserve().add(m);
		
		session.save(m);
		session.save(user);
		
		transaction.commit();
		
		Set<Meeting> meetings = user.getMeeting_reserve();
		
		System.out.println(meetings.size());
		
	}
	
	@Test
	public void test2() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, 1);
		
		
		Meeting m = new Meeting();
		m.setMeetingName("456");
		
		m.setSubscriber(user);
		user.getMeeting_reserve().add(m);
		session.save(m);
		transaction.commit();
		Set<Meeting> set = user.getMeeting_reserve();
		
		System.out.println(set.size());
	}
	
	@Test
	public void test3() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 1);
		Set<Meeting> set = user.getMeeting_reserve();
		System.out.println(set.size());
		transaction.commit();
		
	}
	
	@Test
	public void test4() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date data = sdf.parse("2015-12-12 11:11");
		System.out.println(data);
		Date d = new Date();
		System.out.println(d);
	}
	
	@Test
	public void test5() {
		String json = "{\"access_token\":\"dasfsdfsdfsdgdfgsdfsf\",\"expires_in\":7200}";
		String token = TokenAccess.parseToken(json);
		System.out.println(token);
	}
	@Test
	public void test() {
		String json = "{\"ticket\":\"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm"
 + "3sUw==\",\"expire_seconds\":60,\"url\":\"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI\"}";
		String ticket = TokenAccess.parseTicket(json);
		System.out.println(ticket);
		
	}
	
	@Test
	public void test6() {
		File file = new File("token.txt");
		System.out.println(file.getAbsolutePath());
	}
	
	@Test
	public void test7() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserServiceInterface bean = context.getBean("userService", UserServiceInterface.class);
		List<User> users = bean.findUserByName("浩");
		User u = users.get(0);
		u.setUserPwd("123");
		bean.saveUser(u);
	}
	
	@Test
	public void test8() throws IOException {
		ByteArrayOutputStream out = QRCode.from("http://www.baidu.com").to(ImageType.JPG).stream();
		File file = new File("1.jpg");
		System.out.println(file.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(out.toByteArray());
		fos.flush();
		fos.close();
	}
	
}
