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
					String makeUuid = UUIDUtil.makeUuid();
					addCookie(response,makeUuid,user);//放入cook信息
					return true;
				}
			}else {
				//return ErrorCodeMsg.PHONE_NOTFOUND;//手机不存在
				throw new GlobalException(ErrorCodeMsg.PHONE_NOTFOUND);
			}
		}
	}
	
	/**
	 * @param tokenString 获取缓存的session的数据
	 * @return
	 */
	public MiaoshaUser getByToken(HttpServletResponse response,String tokenString) {//这个方法 在 参数解析器调用了
		if(StringUtils.isEmpty(tokenString)) {
			return null;
		}
		MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token,tokenString, MiaoshaUser.class);
		//在获得到user对象的时候 进行重新设置  达到延长有效期的效果
		if(null!=miaoshaUser) {//判断是否为空
			addCookie(response,tokenString,miaoshaUser);//重新设置
		}
		return miaoshaUser;
	}
	
	/**
	 * @param response 写入cook操作的抽象方法 response 和miaoshauser对象
	 * @param miaoshaUser
	 */
	public void addCookie(HttpServletResponse response,String makeUuid,MiaoshaUser miaoshaUser) {
		//返回真正业务逻辑的部分 到这里已经成功了  分布式session
		redisService.set(MiaoshaUserKey.token, makeUuid, miaoshaUser);//存入redis缓存 前面两个是 key 组合的  后面的是value 
		Cookie cookie = new Cookie(Content.COOK_NAME_TOCK, makeUuid);//设置 
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//设置有效期
		cookie.setPath("/");//网站的根目录
		response.addCookie(cookie);//放入cook即可
	}
}
