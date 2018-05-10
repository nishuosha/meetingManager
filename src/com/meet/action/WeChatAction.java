package com.meet.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.meet.base.User;
import com.meet.util.TokenAccess;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WeChatAction extends ActionSupport {

	
	
	private static final String url_ticket = "http://weixin.qq.com/q/025kNeNDyLde3183BShp1B";

	private static final String Qrcode_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	
//	获取绑定微信的二维码
//	查阅微信开发文档即可了解
	public String getQrcode() {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		String realPath = ServletActionContext.getServletContext().getRealPath("/");
		File file = new File(realPath + "token.txt");
		String token = "";
		try {
			FileInputStream in = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String temp = "";
			while( (temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			token = sb.toString();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User u = (User) session.getAttribute("user");
		String ticket = TokenAccess.getTicket(token, u.getUserID());
		
		String qrcode = TokenAccess.getQrCode(ticket, realPath);

		File file2 = new File(qrcode);
			try {
				FileInputStream fin = new FileInputStream(file2);
				ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
				int length = 0;
				byte[] b = new byte[1024];
				while( (length = fin.read(b, 0, b.length)) != -1) {
					out.write(b, 0, b.length);
				}
				
				out.flush();
				out.close();
				fin.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;
	}
	
}
