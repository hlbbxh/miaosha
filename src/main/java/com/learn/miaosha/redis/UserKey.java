package com.learn.miaosha.redis;

/**
 * @author DELL
 *  a 用户的key   使用用模板方法模式 进行缓存key的封装   》》》 第三步  实现 Userkey
 */
public class UserKey extends BasePrefix{//继承了 最基础的 BasePrefix
	
	//默认永不过期 直接 给前缀即可 
	private UserKey(String prefix) {
		super(prefix);
	}
	public static UserKey getByUserId = new UserKey("userid");
	public static UserKey getByUserName = new UserKey("username");
}
