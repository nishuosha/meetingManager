package com.meet.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class codeServlet extends HttpServlet {

	private static final String APPID = "wxbf815416f5e8488b";
	private static final String appsecret = "c33b4c9a82385e92563160a79d4d3e64";
	private static final String redirect_url = "http://a2686a9c.ngrok.io/MeetingManager/wxLoginServlet";
	//上面三个仅供测试，实际使用需要修改
	private static final String code_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE#wechat_redirect";
	
//	微信登录的二维码
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png"); 
		String redirect = redirect_url;
		System.out.println(redirect);
		String url = code_url.replace("APPID", APPID).replace("REDIRECT_URI", redirect).replace("SCOPE", "snsapi_base");
		System.out.println(url);
		ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).stream();
		
		OutputStream os = response.getOutputStream();
		os.write(out.toByteArray());
		os.flush();
		os.close();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
