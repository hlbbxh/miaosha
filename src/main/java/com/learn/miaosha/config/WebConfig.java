package com.learn.miaosha.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration //表明是配置类
public class WebConfig extends WebMvcConfigurerAdapter{ //继承这个家伙 WebMvcConfigurerAdapter
	
	@Autowired
	private MiaoShaArgumentResolvers miaoShaArgumentResolvers;
	
	//addArgumentResolvers 要用这个
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(miaoShaArgumentResolvers);//直接add即可
	}
}
