package com.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.learn") 加了@mapper 后可以不需要这个 注意属性文件 配置 mapper.xml的classpath
public class MiaoshaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MiaoshaApplication.class, args);
	}
}
