package com.learn.mmall.common;

/**
 * @author DELL 枚举 的常量  
 *
 */
public enum ResponseCode {
	SUCCESS(0,"SUCCESS"),//成功 
	ERROR(1,"ERROR"),//失败
	NEED_LOGIN(10,"NEED_LOGIN"),//需要登录
	ERROR_ARGUMENT(2,"ERROR_ARGUMENT");//参数错误
	
	private final int code;
	private final String desc;
	
	ResponseCode(int code,String desc){
		this.code=code;
		this.desc=desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}
