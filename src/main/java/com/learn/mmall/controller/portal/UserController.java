package com.learn.mmall.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.mmall.common.Const;
import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.dao.UserMapper;
import com.learn.mmall.entity.User;
import com.learn.mmall.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	/**
	 * by:用户登录功能
	 * @param username
	 * @param password
	 * @param session 
	 * @return
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> login(String username,String password,HttpSession session) {
		if(null==username || null==password) {
			return ServerResponse.createByErrorMessage("账号密码都是必填项");
		}
		ServerResponse<User> login = userServiceImpl.login(username, password);
		if (login.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, login.getData());
		}
		return login;
	}
	
	/**
	 * by:登出功能 （删除session信息）
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> login(HttpSession session) {
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess();
	}

	/**
	 * by:注册方法
	 * @param user 
	 * @return
	 */
	@RequestMapping(value = "register",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> register(User user){
		ServerResponse<String> register = userServiceImpl.register(user);
		return register;
	}
	
	/**
	 * by:校验 用户名和 邮箱 地址是否重复
	 * @param str
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "check_valid",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> checkValid(String str,String type){
		ServerResponse<String> register = userServiceImpl.checkVaild(str, type);
		return register;
	}
	
	
	
}
