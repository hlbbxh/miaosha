package com.learn.mmall.service;

import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.entity.User;

public interface IUserService {
	ServerResponse<User> login(String username,String password);
	ServerResponse<String> register(User user);
	ServerResponse<String> checkVaild(String username,String type);
}
