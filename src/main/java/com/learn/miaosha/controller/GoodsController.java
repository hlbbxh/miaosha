package com.learn.miaosha.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learn.miaosha.core.Content;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.service.MiaoshaUserService;
@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private MiaoshaUserService miaoshaUserService;
	
	@Autowired
	private RedisService redisService;
	/**
	 * success 调用
	 * @return
	 */
 	@RequestMapping("/goodslist")
    public String goodslist(HttpServletResponse response,Model model,
    									//@CookieValue(value = Content.COOK_NAME_TOCK,required=false) String cookieToken,
    									//@RequestParam(value =Content.COOK_NAME_TOCK,required=false) String parmCookieToken,
    									MiaoshaUser miaoshaUser
    									) {
 		//有些手机端 不会放在cook 会放在参数里面  可能为空
		/*
		 * if(null==cookieToken && null==parmCookieToken) {//都是空的就去登录 return "login"; }
		 * String token =
		 * StringUtils.isEmpty(parmCookieToken)?cookieToken:parmCookieToken;//三目运算符
		 * MiaoshaUser miaoshaUser =
		 * miaoshaUserService.getByToken(response,token);//从缓存里面获取
		 */ 		//去往商品列表
 		
 		
 		//------------------------------------------------>删除的代码是因为每次都要获取 cookieToken parmCookieToken 就很繁琐
 		// 所以可以选择直接吧user对象直接注入到里面来
 		// 加入了视图解析器
 		if(null==miaoshaUser) {//没有的话就去登陆啦
 			return "login";
 		}
 		model.addAttribute("miaoshaUser", miaoshaUser); 
 		return "goods_list";
    }
 	
}
