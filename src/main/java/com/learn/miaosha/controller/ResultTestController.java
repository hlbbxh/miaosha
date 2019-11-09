package com.learn.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.miaosha.entity.User;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.redis.UserKey;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.result.Result;
import com.learn.miaosha.service.UserService;

import org.springframework.ui.Model;
@Controller
@RequestMapping("/test")
public class ResultTestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisService redisService;
	/**
	 * success 调用
	 * @return
	 */
 	@RequestMapping("/success")
    @ResponseBody
    public Result<String> hello() {
 		return Result.success("你好旅行者");//成功的话直接调用success返回数据
    }
 	
 	/**
 	 * error 失败的调用
 	 * @return
 	 */
 	@RequestMapping("/error")
    @ResponseBody
    public Result<String> helloError() {
 		return Result.error(ErrorCodeMsg.SERVER_Error);//失败的话直接调用ErrorCodeMsg 
    }
 	
 	/**
 	 * test thymeleaf模板的调用
 	 * @param model
 	 * @return
 	 */
 	@RequestMapping("/thymeleaf")
 	public String testThymeleaf(Model model) {
 		model.addAttribute("name", "胡罗卜");
 		return "hello";
 	}
 	
	/**
 	 * test mysql是否连接成功
 	 * @param model
 	 * @return
 	 */
 	@RequestMapping("/mysql/get1")
 	@ResponseBody
 	public Result<User> testMysqlConnection(Model model) {
    	User user = userService.getById(1);
        return Result.success(user);
 	}
 	
	/**
 	 * 测试musql事物 的特性
 	 * @param model
 	 * @return
 	 */
 	//事物 回滚操作
 	@Transactional
 	@RequestMapping("/mysql/tx")
 	@ResponseBody
 	public Result<String> testtrashtion(Model model) {
 		User user1 = new User();
 		user1.setUserId(3);
 		user1.setUserName("滴滴打车");
 		userService.insertUser(user1);
 		
 		User user2 = new User();
 		user2.setUserId(2);
 		user2.setUserName("滴滴打人");
    	userService.insertUser(user2);
    	
        return Result.success("事物处理完成,请查看数据库");
 	}
 	
 	@RequestMapping("/redis/set")
 	@ResponseBody
 	public Result<Boolean> testRedisset(Model model) {
 		User user = new User();
 		user.setUserId(1);
 		user.setUserName("哇哈哈");
 		//存入 redis key
 		redisService.set(UserKey.getByUserId,"", user);// set后的 key  为 ：：UserKey:userid 
        return Result.success(true);
 	}
 	
 	@RequestMapping("/redis/get")
 	@ResponseBody
 	public Result<User> testRedisget(Model model) {
 		User user = redisService.get(UserKey.getByUserId,"",User.class);
        return Result.success(user);
 	}
}
