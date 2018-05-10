package com.meet.dao;

import java.util.List;
import java.util.Set;

import com.meet.base.MeetDate;
import com.meet.base.Meeting;
import com.meet.base.User;
import com.meet.util.TokenAccess;

public interface UserDaoInterface {
//	按照用户名查找用户
	public User findUserByUserName(String username);
//	按照姓名查找用户
	public List<User> findUserByName(String name);
//	添加用户
	public void saveUser(User user);
//	获取除了当前用户之外的其他用户
	public List<User> getAllUserExcludeSelf(User user);
//	按照多个id查找多个用户
	public Set<User> findUserByIds(int[] ids);
//	获取系统所有用户
	public List<User> getAllUser();
//	根据id获取标记的时间段
	public Set<MeetDate> getMeetDateById(int[] ids);
//	修改密码
	public void updatePwd(User user);
//	查找用户
	public User findUserById(User user);
//	查找微信方面的id
	public User findOpenId(String openId);
//	获取登录标志位不为0的用户
	public List<User> findLoginNotZero();
}
