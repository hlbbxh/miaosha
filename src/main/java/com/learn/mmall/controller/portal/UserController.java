package com.learn.mmall.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.mmall.common.Const;
import com.learn.mmall.common.ResponseCode;
import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.entity.User;
import com.learn.mmall.service.impl.UserServiceImpl;

/**
 * @author DELL
 *
 */
@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	/**
	 * by:用户登录功能
	 * parms:账号 密码
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
	 * @param str 值
	 * @param type 校验类型 
	 * @return
	 */
	@RequestMapping(value = "check_valid",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> checkValid(String str,String type){
		ServerResponse<String> register = userServiceImpl.checkVaild(str, type);
		return register;
	}
	
	/**
	 * by：获取当前用户信息  2019年12月3日20:28:37
	 * @param httpSession 
	 * @return
	 */
	@RequestMapping(value = "get_user_info",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> getuserinfo(HttpSession httpSession){
		User userinfo = (User) httpSession.getAttribute(Const.CURRENT_USER);
		if(null!=userinfo) {
			return ServerResponse.createBySuccess(userinfo);
		}
		return ServerResponse.createByErrorMessage("用户没有登录，无法获取当前用户信息");
	}
	
	/**
	 * by:按照用户名称查找密保问题 2019年12月3日20:46:09
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "forget_get_question",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> forgetgetquestion(String username) {
		ServerResponse<String> selectQuestion = userServiceImpl.selectQuestion(username);
		return selectQuestion;
	}
	
	/**
	 * by:验证密码功能 2019年12月3日21:28:30
	 * @param username
	 * @param question
	 * @param answer
	 * @return
	 */
	@RequestMapping(value = "forget_check_question",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> forgetcheckanswer(String username,String question,String answer){
		ServerResponse<String> checkanswer = userServiceImpl.checkanswer(username, question, answer);
		return checkanswer;
	}
	
	/**
	 * by:修改密码 需要传递token 2019年12月3日21:44:20
	 * @param username
	 * @param newpassword
	 * @param forgetToken
	 * @return
	 */
	@RequestMapping(value = "rest_password",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> forgetRestPassword(String username,String newpassword,String forgetToken){
		ServerResponse<String> restPassword = userServiceImpl.restPassword(username, newpassword, forgetToken);
		return restPassword;
	}
	
	/**
	 * by:登录状态下的 修改密码  2019年12月4日20:56:17
	 * @param httpSession
	 * @param passwordOld
	 * @param passwordNew
	 * @return
	 */
	@RequestMapping(value = "rest_password_infoed",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> resetPasswordLogined(HttpSession httpSession,String passwordOld,String passwordNew){
		User user = (User) httpSession.getAttribute(Const.CURRENT_USER);//判断是否登录
		if(null==user) {
			return ServerResponse.createByErrorMessage("用户未登录");
		}
		ServerResponse<String> resultPassword = userServiceImpl.resultPassword(passwordOld, passwordNew, user);
		return resultPassword;
	}
	
	/**
	 * by:登录状态下 更新个人信息 2019年12月4日20:56:13
	 * @param httpSession
	 * @param user_update
	 * @return
	 */
	@RequestMapping(value = "updateInfo",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> update_info(HttpSession httpSession,User user_update){
		User user = (User) httpSession.getAttribute(Const.CURRENT_USER);//判断是否登录
		if(null==user) {
			return ServerResponse.createByErrorMessage("用户未登录");
		}
		user_update.setId(user.getId());
		ServerResponse<User> updateInfo = userServiceImpl.updateInfo(user_update);
		return updateInfo;
	}
	
	/**
	 * by:获取用户信息
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "getInfo",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> getInfo(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(Const.CURRENT_USER);//判断是否登录
		if(null==user) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登录才能访问");
		}
		ServerResponse<User> getuserinfo = userServiceImpl.getuserinfo(user.getId());
		return getuserinfo;
	}
	
}
