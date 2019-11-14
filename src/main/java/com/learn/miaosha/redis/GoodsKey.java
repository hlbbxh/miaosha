package com.learn.miaosha.redis;

/**
 * @author DELL
 *  a url 页面缓存 缓存到redis
 */
public class GoodsKey extends BasePrefix{//继承了 最基础的 BasePrefix
	
	//默认永不过期 直接 给前缀即可 
	private GoodsKey(Integer expInteger,String prefix) {//加入有效期 时间短一点 
		super(expInteger,prefix);
	}
	public static GoodsKey getGoodsList = new GoodsKey(60,"goodslist");
}
