package com.learn.miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.result.Result;

@ControllerAdvice//异常处理
@ResponseBody//方便输出
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)//拦截那些异常
	public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
		e.printStackTrace();//打印异常信息--------------------------------------------------------《《《《《《《
		if(e instanceof GlobalException) {
			//捕获异常 自己定义的异常
			GlobalException globalException =(GlobalException) e;
			return Result.error(globalException.getErrorCodeMsg());
		}else if(e instanceof BindException) {
			BindException bindException = (BindException) e;//强制转换
			List<ObjectError> errors = bindException.getAllErrors();//获取所有的异常
			String defaultMessage = "";
			for (int i = 0; i < errors.size(); i++) {
				ObjectError objectError = errors.get(i);//获取第一个
				defaultMessage = defaultMessage + objectError.getDefaultMessage();
			}
			return Result.error(ErrorCodeMsg.BIND_ERROR.fillArgs(defaultMessage));//返回带参数的
		}else {
			return Result.error(ErrorCodeMsg.SERVER_Error);//服务器异常
		}
	}
}
