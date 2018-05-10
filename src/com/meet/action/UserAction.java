package com.meet.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.meet.base.MeetDate;
import com.meet.base.MeetStatus;
import com.meet.base.Meeting;
import com.meet.base.User;
import com.meet.service.MeetingServiceInterface;
import com.meet.service.UserServiceInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import cn.dsna.util.images.ValidateCode;

@Controller
public class UserAction extends ActionSupport implements ModelDriven<User> {

	private User user = new User();

	// 获取界面输入的验证码
	private String code;

	public void setCode(String code) {
		this.code = code;
	}
	
//	获取页面输入的所有id
	private int[] ids;
	
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	
	public int[] getIds() {
		return ids;
	}
//	页面传的meeting 的id
	private int meetid;
	public void setMeetid(int meetid) {
		this.meetid = meetid;
	}
	public int getMeetid() {
		return meetid;
	}
//	页面传的pwd
	private String pwd;
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwd() {
		return pwd;
	}
//	页面传的newpwd
	private String newpwd;
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	// 注入userService
	@Autowired
	private UserServiceInterface userService;
	// 注入meetingService
	@Autowired
	private MeetingServiceInterface meetingService;

	// 获取验证码，并输出到页面上
	public String code() {
		// 获取request
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取session
		HttpSession session = request.getSession();
		// 获取response
		System.out.println(session);
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置输出内容类型
		response.setContentType("image/jpeg");
		// 生成验证码
		ValidateCode validateCode = new ValidateCode(150, 70, 4, 2);
		// 得到验证码的字符串
		String code = validateCode.getCode();
		// 将验证码放入session
		session.setAttribute("code", code);
		System.out.println(code);

		// 将验证码图片以输出流写到页面上
		try {
			validateCode.write(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 模型驱动
	@Override
	public User getModel() {
		return user;
	}

	// 校验验证码是否正确
	public String valiCode() throws IOException {
		// 获取session
		HttpSession session = ServletActionContext.getRequest().getSession();
		// 获取response
		HttpServletResponse response = ServletActionContext.getResponse();
		// 从session中获得code
		String scode = (String) session.getAttribute("code");
//		如果验证码3为null，则返回0，表示错误
		if (code == null) {
			response.getWriter().write(0);
			return null;
		}
		if (scode.equalsIgnoreCase(code)) {
			response.getWriter().write("1");
		} else {
			response.getWriter().write("0");
		}

		return null;
	}

	// 校验输入的用户名是否已经存在
	public String valiUserName() throws IOException {

		HttpServletResponse response = ServletActionContext.getResponse();
//		根据页面输入的用户名，查找数据库，判断用户名是否存在
		User u = userService.findUserByUserName(user.getUserName());
		if (u == null) {
			// 用户名不存在，可以使用
			response.getWriter().write("1");
		} else {
			// 用户名已存在，不能使用
			response.getWriter().write("0");
		}

		return null;
	}

	// 用户注册
	public String register() {
//		保存用户
		userService.saveUser(user);

		return SUCCESS;
	}

	// 用户登录
	public String login() {
//		获取session
		HttpSession session = ServletActionContext.getRequest().getSession();
//		根据用户名查找用户
		User u = userService.findUserByUserName(user.getUserName());
//		判断密码是否相等
		if (u.getUserPwd().equals(user.getUserPwd())) {
			session.setAttribute("user", u);
		} else {
			ActionContext.getContext().put("error", "密码错误");
			return INPUT;
		}
		return SUCCESS;
	}

	// 我预定的会议
	public String getMyReserve() {

		HttpSession session = ServletActionContext.getRequest().getSession();
//		从session中获取当前登录用户
		User u = (User) session.getAttribute("user");
//		重新获取一次用户，为了新添加的会议能够立刻刷新
		u = userService.findUserByUserName(u.getUserName());
//		上面添加会议的时候，就是为这里服务
		Set<Meeting> meetings_reserve = u.getMeeting_reserve();
		ActionContext.getContext().put("meetings_reserve", meetings_reserve);

		return SUCCESS;
	}

	// 我的参与会议
	public String getMyParticipate() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		User u = (User) session.getAttribute("user");
		// 由于关闭延迟加载，所以在get的时候，一次性获取所有相关数据，并缓存，如果在此后对数据进行修改，那么得到的不是最新的数据，所以必须重新get数据.
		u = userService.findUserByUserName(u.getUserName());
		Set<Meeting> meetings_participate = u.getMeeting_participate();
		ActionContext.getContext().put("meetings_participate", meetings_participate);
		return SUCCESS;
	}

	public String getAllUser() {
//		获取系统中所有会议
		List<User> users = userService.getAllUser();
		ActionContext.getContext().getValueStack().push(users);
		ServletActionContext.getRequest().setAttribute("size", users.size());
		return SUCCESS;
	}

	// 查找用户
	public String searchUser() {

		ValueStack stack = ActionContext.getContext().getValueStack();
		HttpServletRequest request = ServletActionContext.getRequest();
//		按照用户名查找
		if (!"".equals(user.getUserName()) && user.getUserName() != null) {
			stack.push(userService.findUserByUserName(user.getUserName()));
			request.setAttribute("size", 1);
			return SUCCESS;
		}
//		按照姓名查找
		if (user.getName() != "" && user.getUserName() != null) {
			List<User> users = userService.findUserByName(user.getName());
			stack.push(users);
			request.setAttribute("size", users.size());
			return SUCCESS;
		}
//		如果没有按照条件查找，则查找所有用户
		return getAllUser();

	}

	public String getNewMeeting() {
		
		return getMyParticipate();
	}
	
//	标记时间段的action
	public String markTime() {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		User u = (User) session.getAttribute("user");
		Meeting m1 = new Meeting();
		m1.setMeetingID(meetid);
//		根据id查找会议
		Meeting m2 = meetingService.findMeetingById(m1);
//		当前会议的所有时间段
		Set<MeetDate> md1 = m2.getMeetDates();
//		当前用户已经标记的所有时间段
		Set<MeetDate> md2 = u.getDates();
		
//		一个用户可以标记多个会议的多个时间段
//		所以,先移除这个用户已经标记的时间段中与当前会议有关的数据
		
		for(MeetDate md3 : md1) {
			if(md2.contains(md3)) {
				md2.remove(md3);
			}
		}
//		根据页面选取的时间段的id获取对应的时间段
		Set<MeetDate> md4 = userService.getMeetDateById(ids);
//		然后,在此重新添加用户新选择的时间段,就可以实现修改已经标记时间段
		for(MeetDate md5 : md4) {
			md2.add(md5);
		}		
		
		MeetStatus ms = meetingService.findMeetingMarked(m2);
		Set<MeetStatus> msSet = u.getMeetStatus();
		Iterator<MeetStatus> iterator = msSet.iterator();
		while(iterator.hasNext()) {
			MeetStatus mstemp = iterator.next();
			if(mstemp.getMeeting().getMeetingID() == m2.getMeetingID()) {
				iterator.remove();
			}
		}
		
		u.getMeetStatus().add(ms);
		
		meetingService.saveMeeting(m2); 
		userService.saveUser(u);
		return SUCCESS;
	}
	
//	校验密码
	public String valiPwd() {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		User user = (User) session.getAttribute("user");
		if( pwd == "" || pwd == null || !user.getUserPwd().equals(pwd)) {
			try {
				response.getWriter().write("0");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().write("1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
//	修改密码
	public String changePwd() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		user.setUserPwd(newpwd);
		userService.updatePwd(user);
		return SUCCESS;
	}
	
//	获取用户信息
	public String userInfo() {
		User u = userService.findUserById(user);
		ActionContext.getContext().getValueStack().push(u);
		return SUCCESS;
	}

//	退出,未实现
	public String exit() {
		ServletActionContext.getRequest().getSession().invalidate();
		return null;
	}
	
//	微信登录相关
//	前端页面,微信扫码之后,需要不断向服务器发送请求判断是否登录成功,如果登录成功,页面需要跳转
//	微信登录成功之后,会将一个登录标记位置1,只需要检测这个标记位即可
//	检测到标记位,需要使页面跳转,将用户添加到session中,并置标记位0
	public String checkLogin() {
		HttpServletResponse response = ServletActionContext.getResponse();
		
		List<User> users = userService.findLoginNotZero();
		if(users.size() == 0) {
			try {
				response.getWriter().write("0");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User u = users.get(0);
			u.setLogin(0);
			userService.saveUser(u);
			session.setAttribute("user", u);
			try {
				response.getWriter().write("1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
