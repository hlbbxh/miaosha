package com.learn.miaosha.redis;

/**
 * @author DELL
 * aa 使用用模板方法模式 进行缓存key的封装   》》》 第二步  实现 KeyPrefix
 */
public abstract class BasePrefix implements KeyPrefix{//实现接口  定义抽象
	
	//过期时间
	private int expireSeconds;
	
	//前缀 key 
	private String prefix;
	
	// 参数进行判断  只有前缀 用不过期
	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}
	
	//有过期时间要传 过期时间 
	public BasePrefix(int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}
	
	// 如何通过调用呢 通过类名进行调用 就有了 orderkey userkey 这两个类  主要方法
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
