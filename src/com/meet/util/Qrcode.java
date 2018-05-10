package com.meet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.glxn.qrgen.QRCode;

//微信登录二维码
//查阅微信开发文档
public class Qrcode {

	private static final String APPID = "wxbf815416f5e8488b";
	private static final String appsecret = "c33b4c9a82385e92563160a79d4d3e64";
	private static final String redirect_url = "http://99d6e604.ngrok.io/MeetingManager/wxLoginServlet";
	private static final String code_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE#wechat_redirect";
	private static final String TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public static String getOpenId(String code) {
		
		String url = TOKEN.replace("APPID", APPID).replace("SECRET", appsecret).replace("CODE", code);
		
		try {
			URL u = new URL(url);
			
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();

			connection.setDoInput(true);
			int responsecode = connection.getResponseCode();
			
			if(responsecode == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String temp = "";
				StringBuilder sb = new StringBuilder();
				
				while( (temp = reader.readLine()) != null ) {
					sb.append(temp);
				}
				
				return parseToken(sb.toString());
			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parseToken(String jsonToken) {
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonToken);
		JsonObject object = element.getAsJsonObject();
		String openid = object.get("openid").getAsString();
		return openid;
		
	}
	
	
}
