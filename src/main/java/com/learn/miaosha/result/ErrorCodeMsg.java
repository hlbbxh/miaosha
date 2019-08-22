package com.learn.miaosha.result;

/**
 * @author DELL
 *   统一返回错误代码的都在这里
 */
public class ErrorCodeMsg {
	private int code;//错误状态代码
	private String msg;//错误信息
	
	//通用异常
	public static ErrorCodeMsg SERVER_Error = new ErrorCodeMsg(500500,"服务器异常");
	
	//登录模块 5002XX
	
	//商品模块 5003XX
	
	//订单模块 5004XX
	
	//秒杀模块 5005XX
	
	
	public ErrorCodeMsg(int code,String msg) {
		this.code=code;
		this.msg=msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
