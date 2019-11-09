package com.learn.miaosha.result;

/**
 * @author DELL
 *   统一返回错误代码的都在这里
 */
public class ErrorCodeMsg {
	private int code;//错误状态代码
	private String msg;//错误信息
	
	//通用异常
	public static ErrorCodeMsg SUCCESS = new ErrorCodeMsg(200,"success");
	public static ErrorCodeMsg SERVER_Error = new ErrorCodeMsg(500500,"服务器异常");
	
	//登录模块 5002XX
	public static ErrorCodeMsg SESSION_ERROR = new ErrorCodeMsg(500500,"Session不存在或者已经失效");
	public static ErrorCodeMsg PASSWORLD_EMPTY = new ErrorCodeMsg(5000001,"密码不能为空");
	public static ErrorCodeMsg PHONE_EMPTY = new ErrorCodeMsg(5000002,"手机号为为空");
	public static ErrorCodeMsg PHONE_ERROR = new ErrorCodeMsg(5000003,"手机号格式错误");
	public static ErrorCodeMsg ERROR_PARM = new ErrorCodeMsg(5000004,"参数错误");
	public static ErrorCodeMsg PHONE_NOTFOUND = new ErrorCodeMsg(5000005,"手机号码不存在");
	public static ErrorCodeMsg PASSWORD_ERROR = new ErrorCodeMsg(5000006,"密码错误");
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
