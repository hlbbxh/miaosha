package com.learn.mmall.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.dao.UserMapper;
import com.learn.mmall.entity.User;
import com.learn.mmall.service.IUserService;
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ServerResponse<User> login(String username, String password) {
		int checkUsername = userMapper.checkUsername(username);
		if(checkUsername==0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		User selectlogin = userMapper.selectlogin(username, password);
		if(null==selectlogin) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		selectlogin.setPassword(StringUtils.EMPTY);//制空密码
		return ServerResponse.createBySuccess("登录成功",selectlogin);
	}

}
