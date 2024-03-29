package com.learn.miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.result.Result;
import com.learn.miaosha.service.MiaoshaUserService;
import com.learn.miaosha.utils.ValidatorUtil;
import com.learn.miaosha.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Object> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {//jsr303 》》》第一步 加入注解
    	log.info(loginVo.toString());
    	//登录
    	//参数检验
    	String passinput = loginVo.getPassword();//密码
    	String mobString = loginVo.getMobile();//手机
		/*
		 * if(StringUtils.isEmpty(passinput)) { return
		 * Result.error(ErrorCodeMsg.PASSWORLD_EMPTY); //密码为空 }
		 * if(StringUtils.isEmpty(mobString)) { return
		 * Result.error(ErrorCodeMsg.PHONE_EMPTY); //手机号为空 }
		 * if(!ValidatorUtil.isMobile(mobString)) { return
		 * Result.error(ErrorCodeMsg.PHONE_ERROR); //手机号错误 }
		 */
    	//============================》加入了jsr303校验就可以不用判断这些东西了
    	//登录
    	Boolean login = miaoshaUserService.login(response,loginVo);//有异常已经抛出了 没有异常到这里就成功了
    	return Result.success(true);
    }
}
