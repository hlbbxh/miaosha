package com.learn.miaosha.redis;

/**
 * @author DELL
 *  a 不清楚是啥  秒杀用户的前缀
 */
public class MiaoshaUserKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	//有效期
	private MiaoshaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
	public static MiaoshaUserKey getByMiaoshaUserid = new MiaoshaUserKey(0, "userid");//缓存用户的数据 永久有效

}
