package com.meet.action;

import com.meet.base.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description 测试类action，无实际作用
 * @author 流浪者
 * @date  2017年5月29日 上午10:52:18
 */
public class ActionTest extends ActionSupport implements ModelDriven<User> {

	private User user = new User();
	
	@Override
	public User getModel() {
		return user;
	}
	
	public String test() {
		return SUCCESS;
	}

	
}
