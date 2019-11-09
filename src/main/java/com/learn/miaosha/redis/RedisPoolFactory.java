package com.learn.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author DELL
 *  a相当于连接池，获取链接的东西 生产 JedisPool 的 》》》》第二步
 */
@Service
public class RedisPoolFactory {

	@Autowired
	RedisConfig redisConfig;
	
	/**
	 * @return 返回 JedisPool  获取连接 注入配置文件 不使用 redistemple 
	 */
	@Bean
	public JedisPool JedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
		poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
		//获取连接的 参数  有很多的方法  选择适用的
		JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout()*1000, redisConfig.getPassword(), 0);//0 是db 
		return jp;
	}
	
}
