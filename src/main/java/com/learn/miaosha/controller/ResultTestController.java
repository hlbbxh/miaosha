package com.learn.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.miaosha.entity.User;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.result.Result;
import com.learn.miaosha.service.UserService;

import org.springframework.ui.Model;
@Controller
@RequestMapping("/test")
public class ResultTestController {
	
	@Autowired
	private UserService userService;
	
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
 	
}
