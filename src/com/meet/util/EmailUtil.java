package com.meet.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import com.meet.base.User;


/**
 *  这是一个简单javaMail 发送邮件的模板，网上有，不做解释
 * @Description 邮件相关操作
 * @author 流浪者
 * @date  2017年6月6日 下午1:49:24
 */
public class EmailUtil {

//	发件账户名
	public static final String myAccount = "xxx@xxx.com";
//	发件账户密码
	public static final String myPassword = "xxx";
//	邮件服务器
//		根据不同类型的邮箱，填写不同的服务器地址
	public static String myEmailSMTPHost = "smtp.xxx.com";
	
	public static void sendEmail(String[] to, String time, User user) throws Exception {
		
		Properties  props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", myEmailSMTPHost);
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		MimeMessage message = createMimeMessage(session, to, time, user);
		Transport transport = session.getTransport();
		transport.connect(myAccount, myPassword);
		transport.sendMessage(message, message.getAllRecipients());
		
		transport.close();
		
	}
	
	public static MimeMessage createMimeMessage(Session session, String[] to, String time, User user) throws Exception {
		
		int length = to.length;
		Address[] addresses = new InternetAddress[length];
		for(int i = 0 ; i < length ; i ++) {
			addresses[i] = new InternetAddress(to[i]);
		}
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(myAccount, "会议管理系统", "UTF-8"));
		message.setRecipients(MimeMessage.RecipientType.TO, addresses);
		message.setSubject("会议邀请", "UTF-8");
		message.setContent( user.getName() + " 预定的会议时间确定于  " + time + " ， 还请按时参加！", "text/html;charset=UTF-8");
		message.setSentDate(new Date());
		return message;
		
	}
	
	@Test
	public void test() throws Exception {
		User user = new User();
		user.setName("浩");
//		EmailUtil.sendEmail(myAccount, new String[]{"1074118678@qq.com"}, new Date().toString(), user);
	}
}
