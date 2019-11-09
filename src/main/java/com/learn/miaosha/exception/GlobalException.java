package com.learn.miaosha.exception;

import com.learn.miaosha.result.ErrorCodeMsg;

/**
 * @author DELL 全局异常
 *
 */
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	//ErrorCodeMsg 异常信息
	private ErrorCodeMsg errorCodeMsg;
	
	public GlobalException(ErrorCodeMsg msg) {
		super(msg.toString());//调用不调用都可以
		this.errorCodeMsg=msg;
	}

	public ErrorCodeMsg getErrorCodeMsg() {
		return errorCodeMsg;
	}

	public void setErrorCodeMsg(ErrorCodeMsg errorCodeMsg) {
		this.errorCodeMsg = errorCodeMsg;
	}

	
}
