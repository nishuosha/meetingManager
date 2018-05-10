package com.meet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//生成绑定微信号的二维码所需的一些函数
//查阅微信开发文档即可
public class TokenAccess {

	private static final String APPID = "wx054fd3ef26992c82";
	private static final String APPSECRET = "c2f39010dbff6b90bb840cd29de17fee";
	private static final String token_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;


	private static final String TICKET = "gQHR8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNWtOZU5EeUxkZTMxODNCU2hwMUIAAgSDai1ZAwSAOgkA";

	
	public static String getToken(String token_url) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(token_url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");

			int code = connection.getResponseCode();
			System.out.println(code);

			InputStream in = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader bf = new BufferedReader(reader);

			StringBuilder sb = new StringBuilder();
			String temp = "";
			while ((temp = bf.readLine()) != null) {
				sb.append(temp);
			}
			return parseToken(sb.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getTicket(String token, int scene_id) {
		String urlstr = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
		String json = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+ scene_id +"}}}";
		URL url = null;
		HttpURLConnection connection = null;
		OutputStream out = null;
		try {
			url = new URL(urlstr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = connection.getOutputStream();
			out.write(json.getBytes());

			int code = connection.getResponseCode();

			if (code == 200) {

				InputStream in = connection.getInputStream();
				InputStreamReader reader = new InputStreamReader(in);
				BufferedReader bf = new BufferedReader(reader);

				StringBuilder sb = new StringBuilder();
				String temp = "";
				while ((temp = bf.readLine()) != null) {
					sb.append(temp);
				}
				return parseTicket(sb.toString());
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

	public static String getQrCode(String ticket, String path) {

		String ticket_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		URL url = null;
		HttpURLConnection connection = null;
		InputStream in = null;
		FileOutputStream fout = null;
		String allPath = path + "qrcode.jpg";
		File file = new File( allPath);
		try {
			fout = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			url = new URL(ticket_url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);

			int code = connection.getResponseCode();

			if (code == 200) {

				byte[] b = new byte[1024];
				in = connection.getInputStream();
				int length = 0 ;
				while ( (length = in.read(b)) != -1 ) {
					fout.write(b, 0, length);
				}
				fout.flush();
				fout.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		System.out.println("完成");
		return allPath;
	}
	
	public static String parseToken(String jsonToken) {
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonToken);
		JsonObject object = element.getAsJsonObject();
		String token = object.get("access_token").getAsString();
		return token;
	}
	
	public static String parseTicket(String jsonTicket) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonTicket);
		JsonObject object = element.getAsJsonObject();
		String ticket = object.get("ticket").getAsString();
		return ticket;
	}
}
