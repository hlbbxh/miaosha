package com.learn.miaosha.redis;
/**
 * @author DELL
 * a 订单木块key的 前缀
 */
public class OrderKey extends BasePrefix {

	public OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}
