package com.learn.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.resource.HttpResource;

import com.learn.miaosha.core.Content;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.service.MiaoshaUserService;

/**
 * @author DELL  实现 HandlerMethodArgumentResolver  两个方法  最后注册 MiaoShaArgumentResolvers 到webconfig里面来
 *
 */
@Service
public class MiaoShaArgumentResolvers implements HandlerMethodArgumentResolver{

	@Autowired
	private MiaoshaUserService miaoshaUserService;//注入service 你也是一个service 不要忘记
	
	/**
	 * 从这里获取参数类型
	 */ 
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> parameterType = parameter.getParameterType();//获取参数类型
		return parameterType==MiaoshaUser.class;//判断是不是我们的秒杀user  是miaoshuauser 才进行 resolveArgument处理
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);//request 
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);//获取response
		
		String parmToken = request.getParameter(Content.COOK_NAME_TOCK);//request获取参数
		String cookieToken = getCookie(request,Content.COOK_NAME_TOCK);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(parmToken) ) {
			return null;
		}
		String token = StringUtils.isEmpty(parmToken)?cookieToken:parmToken;//三目运算符
		return miaoshaUserService.getByToken(response,token);//从缓存里面获取 返回ta
	}

	/**
	 * @param request
	 * @param cookNameTock 获取cookie 遍历 是不是有这个 cookie
	 * @return
	 */
	private String getCookie(HttpServletRequest request, String cookName) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals(cookName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
