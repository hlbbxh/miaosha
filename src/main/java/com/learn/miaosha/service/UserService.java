package com.learn.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.miaosha.dao.UserDao;
import com.learn.miaosha.entity.User;
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public User getById(int id) {
		return userDao.getById(id);
	}
	
	public int insertUser(User user) {
		return userDao.insert(user);
	}

}	
