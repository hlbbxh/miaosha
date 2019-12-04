package com.learn.mmall.common;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author DELL 相当于一个redis的效果 后期可以换成redis进行操作
 *
 */
public class TokenCache {
	
	//slf4j 的日志
	private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
	
	public static final String TOKEN_PREFIX = "token_";
	
	// initialCapacity 缓存的初始化大小  maximumSize缓存大小lru算法  有效期：expireAfterAccess
	private static LoadingCache<String,String> localCatch = CacheBuilder.newBuilder()
			.initialCapacity(1000)
			.maximumSize(10000)
			.expireAfterAccess(12,TimeUnit.HOURS).build(new CacheLoader<String, String>(){
				@Override
				public String load(String s) throws Exception{//key 没有命中 调用该方法
					return null;
				}
			});
	
	//设置
	public static void setKey(String key,String value) {
		localCatch.put(key, value);
	}
	
	//获取
	public static String getkey(String key) {
		try {
			String string = localCatch.get(key);
			if(null!=string) {
				return string;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
