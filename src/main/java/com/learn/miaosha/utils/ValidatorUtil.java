package com.learn.miaosha.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author DELL 校验工具类
 *
 */
public class ValidatorUtil {
	
	//手机号的正则表达式
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	//判断是不是手机号
	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(isMobile("13296642033"));//true
		System.out.println(isMobile("1329966645a"));//false
	}
}
