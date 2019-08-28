package com.learn.miaosha.redis;

/**
 * @author DELL
 * a接口 相当于 获取前缀的  key太多会重复  现在各个模块添加不同的前缀
 */
public interface KeyPrefix {
	
	public int expireSeconds();
	
	public String getPrefix();
	
}
