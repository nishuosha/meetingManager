package com.meet.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.meet.base.TextMessage;
import com.meet.base.User;
import com.meet.dao.UserDaoInterface;
import com.meet.dao.UserDaoInterfaceImpl;
import com.meet.service.UserServiceInterface;
import com.meet.service.UserServiceInterfaceImpl;
import com.meet.util.MessageUtil;
import com.meet.util.TokenAccess;


public class WxServlet extends HttpServlet {

	private static final String APPID_TEST = "wxbf815416f5e8488b";
	private static final String APPSECRET_TEST = "c33b4c9a82385e92563160a79d4d3e64";
	private static final String token_URL_TEST = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ APPID_TEST + "&secret=" + APPSECRET_TEST;
	
	
//	微信的token有效期只有两个小时,所以需要每隔两个小时重新获取
	@Override
	public void init() throws ServletException {
		
		String realPath = getServletContext().getRealPath("/");
		File file = new File(realPath + "token.txt");
		String path = file.getAbsolutePath();
		String token = TokenAccess.getToken(token_URL_TEST);	
		System.out.println(path);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(token.getBytes());
			fout.flush();
			
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		线程,两个小时之后重新获取token
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(7000 * 1000);
						String t2 = TokenAccess.getToken(token_URL_TEST);
						FileOutputStream fo = new FileOutputStream(file);
						fo.write(t2.getBytes());
						fo.flush();
						fo.close();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
		}});
		
		thread.start();
	}
	
//	微信服务器需要将消息映射到另外一个服务器地址,需要先校验
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("开始校验");

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String token = "hao";

		String list = sort(token, timestamp, nonce);

		String mysignature = sha1(list);

		if (mysignature != null && mysignature != "" && mysignature.equals(signature)) {
			System.out.println("校验成功");
			response.getWriter().write(echostr);
		} else {
			System.out.println("校验失败");
		}
	}
	
//	根据事件类型,返回不同的结果
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
//			将equest的xml转为map
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String MsgType = map.get("MsgType");
			String event = map.get("Event");
			String eventKey = map.get("EventKey");
			String ticket = map.get("Ticket");
			
//			事件类型
			if("event".equals(MsgType)) {
				
//				未关注,扫码事件
				if("subscribe".equals(event)) {
					//说明是新的关注
					String key = eventKey.replace("qrscene_", "");
					System.out.println(key);
					int id = Integer.parseInt(key);
					
					User user = new User();
					user.setUserID(id);
						
					ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
					UserServiceInterface userService = context.getBean("userService", UserServiceInterface.class);
					User u = userService.findUserById(user);
					System.out.println(u.getName() + u.getUserName() + u.getEmail());
					
					TextMessage text = new TextMessage();
					text.setToUserName(fromUserName);
					text.setFromUserName(toUserName);
					text.setMsgType("text");
					text.setCreateTime(new Date().getTime());
					text.setContent("绑定成功" + "\n" + 
									"您的信息为：" + "\n" +
									"用户名:" + u.getUserName() + "\n" +
									"密码为：" + u.getUserPwd() + "\n" + 
									"姓名为：" + u.getName()  + "\n" + 
									"联系方式：" + u.getPhone() + "\n" +
									"邮箱:" + u.getEmail()
							);
					
					String xml = MessageUtil.toXML(text);
					System.out.println(xml);
					
					u.setOpenid(fromUserName);
					u.setStatus(1);
					userService.saveUser(u);
					
					response.getWriter().write(xml);
				}
				
//				已关注,扫码事件
				if("SCAN".equals(event)) {
					int id = Integer.parseInt(eventKey);
					User user = new User();
					user.setUserID(id);
					ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
					UserServiceInterface userService = context.getBean("userService", UserServiceInterface.class);
					User u = userService.findUserById(user);
					
					TextMessage text = new TextMessage();
					text.setToUserName(fromUserName);
					text.setFromUserName(toUserName);
					text.setMsgType("text");
					text.setCreateTime(new Date().getTime());
					text.setContent("您已绑定" + "\n" + 
									"您的信息为：" + "\n" +
									"用户名:" + u.getUserName() + "\n" +
									"密码为：" + u.getUserPwd() + "\n" + 
									"姓名为：" + u.getName()  + "\n" + 
									"联系方式：" + u.getPhone() + "\n" +
									"邮箱:" + u.getEmail()
							);
					
					u.setOpenid(fromUserName);
					u.setStatus(1);
					userService.saveUser(u);
					String xml = MessageUtil.toXML(text);
					response.getWriter().write(xml);
				}
				
//				取消关注事件
				if("unsubscribe".equals(event)) {
					
					ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
					UserServiceInterface userService = context.getBean("userService", UserServiceInterface.class);
					User u = userService.findOpenId(fromUserName);
					if(u != null) {
						u.setStatus(0);
						u.setOpenid(null);
						userService.saveUser(u);
					}
					System.out.println(fromUserName + "取消关注此公众号");
				}
			}
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

//	一个函数,为了进行校验
//	查阅微信开发文档即可
	
//	字典排序方法
	public String sort(String token, String timestamp, String nonce) {
		List<String> list = new ArrayList<String>();
		list.add(token);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);

		StringBuilder builder = new StringBuilder();
		for (String s : list) {
			builder.append(s);
		}

		return builder.toString();
	}

//	hash加密方法
	public String sha1(String string) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(string.getBytes());
			byte[] messageDigest = digest.digest();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					sb.append(0);
				}
				sb.append(shaHex);
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
}
