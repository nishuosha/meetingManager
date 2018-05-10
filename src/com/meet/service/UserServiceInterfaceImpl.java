package com.meet.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meet.base.MeetDate;
import com.meet.base.Meeting;
import com.meet.base.User;
import com.meet.dao.UserDaoInterface;

@Service("userService")
public class UserServiceInterfaceImpl implements UserServiceInterface {

	@Autowired
	private UserDaoInterface userDao;
	
	@Override
	public User findUserByUserName(String username) {
		return userDao.findUserByUserName(username);
	}

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}


	@Override
	public List<User> getAllUserExcludeSelf(User user) {
		return userDao.getAllUserExcludeSelf(user);
	}

	@Override
	public Set<User> findUserById(int[] ids) {
		return userDao.findUserByIds(ids);
	}

	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public List<User> findUserByName(String name) {
		return userDao.findUserByName(name);
	}

	@Override
	public Set<MeetDate> getMeetDateById(int[] ids) {
		return userDao.getMeetDateById(ids);
	}

	@Override
	public void updatePwd(User user) {
		userDao.updatePwd(user);
	}

	@Override
	public User findUserById(User user) {
		return userDao.findUserById(user);
	}

	@Override
	public User findOpenId(String openId) {
		return userDao.findOpenId(openId);
	}

	@Override
	public List<User> findLoginNotZero() {
		return userDao.findLoginNotZero();
	}

}
