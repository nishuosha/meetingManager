package com.meet.filter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.meet.base.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

//登录拦截器
//在没有session的情况下，无法直接访问某个action
public class LoginFilter extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			ActionContext.getContext().put("error", "未登录");
			return "login";
		}
		
		return arg0.invoke();
	}

}
