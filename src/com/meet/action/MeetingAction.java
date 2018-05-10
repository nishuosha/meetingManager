package com.meet.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.jdbc.Sql;

import com.meet.base.MeetDate;
import com.meet.base.MeetStatus;
import com.meet.base.Meeting;
import com.meet.base.User;
import com.meet.service.MeetingServiceInterface;
import com.meet.service.UserServiceInterface;
import com.meet.util.EmailUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

@Controller
public class MeetingAction extends ActionSupport implements ModelDriven<Meeting> {

	private static final long serialVersionUID = 1L;

	// 注入meetingService
	@Autowired
	private MeetingServiceInterface meetingService;

	// 注入userService
	@Autowired
	private UserServiceInterface userService;

	// 模型驱动
	private Meeting meeting = new Meeting();

	@Override
	public Meeting getModel() {
		return meeting;
	}

	// 从页面传过来的参与会议的user的id
	private int[] ids;
	
	public void setIds(int[] ids) {
		this.ids = ids;
	}

	public int[] getIds() {
		return ids;
	}

//	从页面传过来的时间段，多个，需要使用数组
	private String[] startdate;
	private String[] starttime;
	private String[] enddate;
	private String[] endtime;

	public String[] getStartdate() {
		return startdate;
	}

	public void setStartdate(String[] startdate) {
		this.startdate = startdate;
	}

	public String[] getStarttime() {
		return starttime;
	}

	public void setStarttime(String[] starttime) {
		this.starttime = starttime;
	}

	public String[] getEnddate() {
		return enddate;
	}

	public void setEnddate(String[] enddate) {
		this.enddate = enddate;
	}

	public String[] getEndtime() {
		return endtime;
	}

	public void setEndtime(String[] endtime) {
		this.endtime = endtime;
	}

	// 添加会议
	public String addMeeting() {
		HttpSession session = ServletActionContext.getRequest().getSession();
//		获取当前登录用户
		User user = (User) session.getAttribute("user");
//		设置会议的预定时间
		meeting.setReserationTime(new Date());
		
//		设置会议的预定者
		meeting.setSubscriber(user);
//		当前用户的预定会议的集合里面添加这个会议。如果没有这句代码，同样也会自动添加，但是页面不能立即显示出新添加的会议。
		user.getMeeting_reserve().add(meeting);
//		根据页面的user的id找到对应的用户
		Set<User> users = userService.findUserById(ids);
		String[] to = new String[users.size()];
//			为了获取所有参与者的邮箱
		List<String> emailList = new ArrayList<String>();
//		设置会议的参与者
		meeting.setParticipants(users);
//		保存这条会议
		meetingService.saveMeeting(meeting);
		System.out.println(meeting.getMeetingID());
		
		MeetStatus s1 = new MeetStatus();
		s1.setStatus(0);
		s1.setMeeting(meeting);
		for(User uu : users) {
			emailList.add(uu.getEmail());
			s1.getUsers().add(uu);
		}
		meetingService.saveMeetStatus(s1);
		
		MeetStatus s2 = new MeetStatus();
		s2.setStatus(1);
		s2.setMeeting(meeting);
		meetingService.saveMeetStatus(s2);
		
		int a = startdate.length;
		Date[] startTime = new Date[a];
		Date[] endTime = new Date[a];
		try {
//			获取数组中的值,拼凑开始时间和结束时间
			startTime = getDateTime(startdate, starttime);
			endTime = getDateTime(enddate, endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Set<MeetDate> dates = new HashSet<MeetDate>();
//		每天会议对应多个时间段,所以每一个都需要添加
		for(int i = 0 ; i < startTime.length ; i ++) {
			MeetDate m = new MeetDate();
			m.setStartTime(startTime[i]);
			m.setEndTime(endTime[i]);
			m.setMeeting(meeting);
			dates.add(m);
			meetingService.saveMeetDate(m);
		}
		
		for (User u : users) {
//			同样是为了页面可以立刻刷新新添加的信息
			u.getMeeting_participate().add(meeting);
			u.getMeetStatus().add(s1);
		}
		
		for(int i = 0 ; i < emailList.size() ; i++) {
			to[i] = emailList.get(i);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
//		下面这些代码写的非常复杂，但是没办法，我只能这样写，担待一下
		
		
		
//		这是一个定时器
		Timer timer = new Timer();
//		这是定时器要执行的任务
//		这个任务的作用是：在会议有效时间结束之后，向参与会议的所有人员发送会议邀请。
//					当代码执行到这里的时候，肯定有参与用户尚未标记时间段。
//					因为一旦所有用户都标记了时间段，就会被下面那个线程检测到，就会发送邮件，然后将这个任务取消掉。
//					所以，这里要做的事，就是在已标记用户标记的时间段中任选一个作为会议的确定时间
		TimerTask task = new TimerTask() {
		
			@Override
			public void run() {
//				遍历会议的参与者
				for(User u : users) {
//					重新获取数据，原因以前写过
					u = userService.findUserById(u);
//					获取当前用户标记的所有会议的所有时间段
					Set<MeetStatus> meetStatus = u.getMeetStatus();
					for(MeetStatus mm : meetStatus) {
//						如果其中有会议满足这个条件，则确定会议的时间
						if(mm.getMeeting().getMeetingID() == meeting.getMeetingID() && mm.getStatus() == 1) {
							Set<MeetDate> mds = u.getDates();
							for(MeetDate mdd : mds) {
								if(mdd.getMeeting().getMeetingID() == meeting.getMeetingID()) {
									String start = sdf.format(mdd.getStartTime());
									String end = sdf.format(mdd.getEndTime());
									System.out.println(start + "-" + end);
									try {
										EmailUtil.sendEmail(to, start + "--" + end, user);
										return ;
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
				
//				当然，还有一种可能就是，有效时间结束了，参与者都没有标记时间段
//				此时，从会议的所有时间段中任选一个作为开始时间
				Set<MeetDate> meetd = meeting.getMeetDates();
				for(MeetDate mda : meetd) {
					String start = sdf.format(mda.getStartTime());
					String end = sdf.format(mda.getEndTime());
					try {
						EmailUtil.sendEmail(to, start + "--" + end , user);
						return ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, +meeting.getEffect()*60 + 1);
//		此任务在多久后执行
		timer.schedule(task, c.getTime());
		
		
		
//		这是上面提到的线程，他会在后台不断检测所有参与者的标记状态
//		一旦所有参与者全部标记，则确定时间，并发送邮件
//		此时，存在以下情况
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int flag = 0;
				int size = 0;
				while(true) {
					size = 0;
					for(User u : users) {
						u = userService.findUserById(u);
						Set<MeetStatus> mss = u.getMeetStatus();
						for(MeetStatus stu : mss) {
							if(stu.getMeeting().getMeetingID() == meeting.getMeetingID() && stu.getStatus() == 1) {
								size ++;
							}
						}
					}
					List<Date> datelist = new ArrayList<Date>();
//					用来检测是否所有参与者都已经标记的时间段
					if(size == users.size()) {
						//获取共同标记的时间
						for(User u : users) {
							u = userService.findUserById(u);
							Set<MeetDate> mds = u.getDates();
							for(MeetDate md : mds ) {
								if(md.getMeeting().getMeetingID() == meeting.getMeetingID()) {
									datelist.add(md.getStartTime());
									datelist.add(md.getEndTime());
								}
							}
						}
						
//					情况一:三个人同时标记了某一个具体时间段,那么这个时间段就作为会议的开始时间
						for(int i = 0 ; i < datelist.size(); i=i+2) {
							int cishu = 1;
							for(int j = 2 ; j < datelist.size(); j=j+2) {
								if(datelist.get(i).getTime() == datelist.get(j).getTime()) {
									cishu ++;
								}
								
								if(cishu == users.size()) {
									String s = sdf.format(datelist.get(i));
									String e = sdf.format(datelist.get(i + 1));
									try {
										EmailUtil.sendEmail(to, s + "--" + e, user);
										timer.cancel();
										return ;
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
						}
						
//						情况二:所有参与者并没有标记同一时间段,那么选取标记的任一时间段作为会议的开始时间
						
						String s = sdf.format(datelist.get(0).getTime());
						String e = sdf.format(datelist.get(1).getTime());
						try {
							EmailUtil.sendEmail(to, s + "--" + e, user);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
//						如果成功发送邮件,那么取消定时器,同时退出线程
						timer.cancel();
						return ;
						
					}
//					如果有人尚未标记,则继续循环执行线程
//					每隔10s检测一次,如果执行了有效时间次循环,还是有人尚未标记,则退出线程,剩下的事交给上面的定时器
					if(flag == meeting.getEffect()*360) {
						return ;
					}
					flag ++;
					try {
						Thread.sleep(1000*10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		t.start();
		
		
		return SUCCESS;
	}
//	根据date数组 和 time数组构造时间段
	public Date[] getDateTime(String[] date, String[] time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date[] dates = new Date[date.length];
		for(int i = 0 ; i < date.length ; i++) {
			dates[i] = sdf.parse(date[i] + " " + time[i]);
		}
		return dates;
	}
	

	// 前往会议预定页面
//	前往预定会议界面,需要获取系统中除了当前登录用户之外的其他用户,因为需要邀请参与会议
	public String toBookMeeting() {

		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute("user");
//		获取系统中除了当前登录用户之外的其他用户
		List<User> users = userService.getAllUserExcludeSelf(user);

		ActionContext.getContext().getValueStack().push(users);
		return SUCCESS;
	}

	// 查看预定会议的细节
	public String getReserveDetail() {

//		根据会议id获取会议详情
		Meeting m = meetingService.findMeetingById(meeting);
//		每个会议对应的参与者,以及每个参与者标记的时间段
		Map<User, Set<MeetDate>> map = new HashMap<User, Set<MeetDate>>();
		
//		获取这个会议的所有参与者
		Set<User> users = m.getParticipants();
//		遍历所有参与者
		for(User u : users) {
//			获得参与者标记的时间段
			Set<MeetDate> md1 = u.getDates();
			
			Set<MeetDate> md2 = new HashSet<MeetDate>();
//			因为一个用户可以标记多个会议,每个会议也可以标记多个时间段
//			如果当前用户标记的所有会议中的某个会议id和当前要查看的会议id相同,那么获取对应的标记的时间段
			for(MeetDate m2 : md1) {
				if(m2.getMeeting().getMeetingID() == m.getMeetingID()) {
					md2.add(m2);
				}
			}
			map.put(u, md2);
			
		}
		ActionContext.getContext().put("map", map);
		ActionContext.getContext().getValueStack().push(m);

		return SUCCESS;
	}

	// 前往撤销会议界面
	public String todeleteMeeting() {
//		根据id查找会议
		Meeting m = meetingService.findMeetingById(meeting);
		
		ActionContext.getContext().getValueStack().push(m);
		return SUCCESS;
	}

	// 撤销会议
	public String deleteMeeting() {
//		根据id查找会议
		Meeting m = meetingService.findMeetingById(meeting);
//		删除会议
		meetingService.deleteMeeting(m);
		return SUCCESS;
	}

	// 查看参与会议详情
	public String getParticipateDetail() {
//		和获取预定会议详情是同样的逻辑
		return getReserveDetail();
	}

	// 前往搜索会议界面
	public String toSearchMeeting() {
//		获取系统中所有的会议
		List<Meeting> meetings = meetingService.getAllMeeting();
		ServletActionContext.getRequest().setAttribute("size", meetings.size());
		ActionContext.getContext().getValueStack().push(meetings);
		return SUCCESS;
	}
//	获取当前会议的所有时间段
	public String getAllTime() {
		
		Meeting m = meetingService.getTimeById(meeting);
//		根据会议id获取添加会议时 选取的所有时间段
		Set<MeetDate> meetDates = m.getMeetDates();
	
		System.out.println(meetDates.size());
		ActionContext.getContext().getValueStack().push(meetDates);
		ActionContext.getContext().put("meetid", m.getMeetingID());
		
		return SUCCESS;
	}
	
//	检测前台输入的时间段是否可用
	public String checkTime() {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		User user = (User) session.getAttribute("user");
		
		int a = startdate.length;

		char[] test = new char[a];
		for(int x = 0 ; x < test.length ; x ++) {
			test[x] = '1';
		}
		
		Date[] startTime = new Date[a];
		Date[] endTime = new Date[a];
		try {
//			得到前台输入的开始时间
			startTime = getDateTime(startdate, starttime);
//			得到前台输入的结束时间
			endTime = getDateTime(enddate, endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		在当前用户所标记的所有时间段中查找，是否存在输入的时间段
		Set<MeetDate> mds = user.getDates();
		for(MeetDate md : mds) {
			Date start = md.getStartTime();
			Date end = md.getEndTime();
			
			for(int i = 0 ; i < a ; i++) {
				if(!(start.getTime() >= endTime[i].getTime() || end.getTime() <= startTime[i].getTime())) {
					test[i] = '0';
				}
			}
		}
		
//		在当前用户所预定的所有会议的时间段中查找，是否存在输入的时间段
		Set<Meeting> ms = user.getMeeting_reserve();
		for(Meeting m : ms) {
			Set<MeetDate> mdset = m.getMeetDates();
			for(MeetDate md : mdset) {
				Date start = md.getStartTime();
				Date end = md.getEndTime();
				
				for(int i = 0 ; i < a ; i++) {
					if(!(start.getTime() >= endTime[i].getTime() || end.getTime() <= startTime[i].getTime())) {
						test[i] = '0';
					}
				}
			}
		}
		
		
		try {
			response.getWriter().write(test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
