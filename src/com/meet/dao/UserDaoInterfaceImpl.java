package com.meet.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.concurrent.SuccessCallback;

import com.meet.base.MeetDate;
import com.meet.base.Meeting;
import com.meet.base.User;


public class UserDaoInterfaceImpl extends HibernateDaoSupport implements UserDaoInterface {
	

	@Override
	public User findUserByUserName(String username) {
		
		List<User> users = (List<User>) this.getHibernateTemplate().find("from User u where u.UserName = ? ", username);
		if(users.size() != 1) {
			return null;
		}
		return users.get(0);
	}
	

	@Override
	public void saveUser(User user) {
		this.getHibernateTemplate().saveOrUpdate(user);
	}



	@Override
	public List<User> getAllUserExcludeSelf(User user) {
		return (List<User>) this.getHibernateTemplate().find("from User u where u.UserID not in (?)", user.getUserID());
	}


	@Override
	public Set<User> findUserByIds(int[] ids) {
		Set<User> users = new HashSet<User>();
		
		for(int i = 0 ; i < ids.length ; i++) {
			User u = this.getHibernateTemplate().get(User.class, ids[i]);
			users.add(u);
		}
		return users;
	}


	@Override
	public List<User> getAllUser() {
		return (List<User>) this.getHibernateTemplate().find("from User");
	}


	@Override
	public List<User> findUserByName(String name) {
		return (List<User>) this.getHibernateTemplate().find("from User u where u.Name = ?", name);
	}


	@Override
	public Set<MeetDate> getMeetDateById(int[] ids) {
		Set<MeetDate> meetDates = new HashSet<MeetDate>();
		
		for(int i = 0 ; i < ids.length ; i++) {
			MeetDate m = this.getHibernateTemplate().get(MeetDate.class, ids[i]);
			meetDates.add(m);
		}
		
		return meetDates;
	}


	@Override
	public void updatePwd(User user) {
		this.getHibernateTemplate().update(user);
	}


	@Override
	public User findUserById(User user) {
		List<User> users = (List<User>) this.getHibernateTemplate().find("from User u where u.UserID = ?", user.getUserID());
		return users.get(0);
	}


	@Override
	public User findOpenId(String openId) {
		List<User> users = (List<User>) this.getHibernateTemplate().find("from User u where u.openid = ?", openId);
		if(users.size() == 0) {
			return null;
		}
		return users.get(0);
		
	}


	@Override
	public List<User> findLoginNotZero() {
		List<User> users = (List<User>) this.getHibernateTemplate().find("from User u where u.login != 0");
		return users;
	}


}
