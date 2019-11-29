package com.learn.mmall.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.mmall.common.Const;
import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.entity.User;
import com.learn.mmall.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value = "login",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> login(String username,String password,HttpSession session) {
		ServerResponse<User> login = userServiceImpl.login(username, password);
		if (login.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, login.getData());
		}
		return login;
	}
	
}
