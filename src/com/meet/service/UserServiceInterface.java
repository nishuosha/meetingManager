package com.meet.service;

import java.util.List;
import java.util.Set;

import com.meet.base.MeetDate;
import com.meet.base.Meeting;
import com.meet.base.User;

public interface UserServiceInterface {

	public User findUserByUserName(String username);
	
	public List<User> findUserByName(String name);
	
	public void saveUser(User user);
	
	public List<User> getAllUserExcludeSelf(User user);
	
	public Set<User> findUserById(int[] ids);
	
	public List<User> getAllUser();
	
	public Set<MeetDate> getMeetDateById(int[] ids);
	
	public void updatePwd(User user);
	
	public User findUserById(User user);
	
	public User findOpenId(String openId);
	
	public List<User> findLoginNotZero();
}
