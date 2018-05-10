package com.meet.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.meet.base.User;
import com.meet.service.UserServiceInterface;
import com.meet.util.Qrcode;

//	实现微信登录功能
public class WxLoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String code = request.getParameter("code");
//		扫码用户的openid
		String openId = Qrcode.getOpenId(code);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserServiceInterface bean = context.getBean("userService", UserServiceInterface.class);
//		根据openid查找对应用户
		User u = bean.findOpenId(openId);
		if( u == null) {
			response.getWriter().write("您尚未绑定账号，登录后绑定微信号，即可微信登录");
		} else {
//			改变登录标志位
			u.setLogin(u.getUserID());
			bean.saveUser(u);
			response.getWriter().write("登录成功");
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		
	}
}
