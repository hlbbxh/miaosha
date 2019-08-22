package com.learn.miaosha.result;
/**
 * @author hlb
 * 统一返回结果
 * 
 */
public class Result<T>{
	
	private int code;//状态码
	private String msg;//信息
	private T date;//数据
	
	
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
	public T getDate() {
		return date;
	}
	public void setDate(T date) {
		this.date = date;
	}
	
	//构造方法  成功的时候调用  只需要data即可
	private Result(T data){
		this.code=200;
		this.msg="success";
		this.date=data;
	}	
	//失败的时候调用
	private Result(ErrorCodeMsg errorCodeMsg) {
		if(errorCodeMsg == null) {
			return;
		}
		this.code = errorCodeMsg.getCode();
		this.msg = errorCodeMsg.getMsg();
	}
	
	/**
	 * 成功时候的调用
	 * */
	public static <T> Result<T> success(T data){
		return new  Result<T>(data);//不通的参数对应不同的构造方法 
	}
	
	/**
	 * 失败时候的调用
	 * */
	public static <T> Result<T> error(ErrorCodeMsg errorCodeMsg){
		return new  Result<T>(errorCodeMsg);//不通的参数对应不同构造方法 
	}
	
}
