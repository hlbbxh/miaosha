package com.learn.miaosha.redis;

/**
 * @author DELL  >>>  使用用模板方法模式 进行缓存key的封装   》》》 第一步
 * a接口 相当于 获取前缀的  key太多会重复  现在各个模块添加不同的前缀
 */
public interface KeyPrefix {
	//缓存时间
	public int expireSeconds();
	//前缀名称
	public String getPrefix();
	
}
