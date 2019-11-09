package com.learn.miaosha.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.learn.miaosha.validator.Mobile;

/**
 * @author DELL
 * vo 入参实体  登录需要用的手机号 和密码
 */
public class LoginVo {
	
	//jsr303 》》》第二步 写入注解
	@NotNull
	@Mobile
	private String mobile;
	
	@NotNull
	@Length(min = 32)
	private String password;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
	}
}
