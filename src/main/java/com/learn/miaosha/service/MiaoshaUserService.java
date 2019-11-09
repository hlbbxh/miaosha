package com.learn.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.miaosha.core.Content;
import com.learn.miaosha.dao.MiaoshaUserDao;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.exception.GlobalException;
import com.learn.miaosha.redis.MiaoshaUserKey;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.utils.MD5Util;
import com.learn.miaosha.utils.UUIDUtil;
import com.learn.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {
	
	@Autowired
	private MiaoshaUserDao miaoshaUserDao;
	
	@Autowired
	private RedisService redisService;
	
	//按照手机号获取密码
	public MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}
	
	//数据校验层
	public Boolean login(HttpServletResponse response,LoginVo loginVo) {
		if(null==loginVo) {
			//return ErrorCodeMsg.ERROR_PARM;//参数错误 参数为空
			throw new GlobalException(ErrorCodeMsg.ERROR_PARM);
		}else{
			String mobile = loginVo.getMobile();
			String formPass = loginVo.getPassword();
			//手机号是否存在
			MiaoshaUser user = getById(Long.parseLong(mobile));
			if(user != null) {
				//验证密码
				String dbPass = user.getPassword();//数据库密码
				String saltDB = user.getSalt();//数据库的盐
				// 数据库的盐 加用户输入的密码
				String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);//计算数据库密码 
				if(!calcPass.equals(dbPass)) {
					//return ErrorCodeMsg.PASSWORD_ERROR;//密码错误
					throw new GlobalException(ErrorCodeMsg.PASSWORD_ERROR);
				}else {
					//返回真正业务逻辑的部分 到这里已经成功了  分布式session
					String makeUuid = UUIDUtil.makeUuid();
					redisService.set(MiaoshaUserKey.token, makeUuid, user);//存入redis缓存 前面两个是 key 组合的  后面的是value
					Cookie cookie = new Cookie(Content.COOK_NAME_TOCK, makeUuid);
					cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//设置有效期
					cookie.setPath("/");//网站的根目录
					response.addCookie(cookie);//放入cook即可
					return true;
				}
			}else {
				//return ErrorCodeMsg.PHONE_NOTFOUND;//手机不存在
				throw new GlobalException(ErrorCodeMsg.PHONE_NOTFOUND);
			}
		}
	}
	
	public MiaoshaUser getByToken(String tokenString) {
		if(StringUtils.isEmpty(tokenString)) {
			return null;
		}
		MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token,tokenString, MiaoshaUser.class);
		return miaoshaUser;
	}

}
