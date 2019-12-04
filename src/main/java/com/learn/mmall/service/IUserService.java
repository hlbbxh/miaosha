package com.learn.mmall.service;

import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.entity.User;

public interface IUserService {
	ServerResponse<User> login(String username,String password);
	ServerResponse<String> register(User user);
	ServerResponse<String> checkVaild(String username,String type);
	ServerResponse<String> selectQuestion(String username);
	ServerResponse<String> checkanswer(String username,String question,String answer);
	ServerResponse<String> restPassword(String username,String newpassword,String forgetToken);
	ServerResponse<User> updateInfo(User user);
	ServerResponse<User> getuserinfo(Integer userid);
}
