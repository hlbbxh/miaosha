package com.learn.mmall.common;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author DELL 统一返回体
 *
 * @param <T>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //为空的 就直接过滤了  没了 
public class ServerResponse<T> implements Serializable{
	private int status;
	private String msg;
	private T data;
	
	//私有化构造方法
	
	private ServerResponse(int status) {
		this.status=status;
	}
	private ServerResponse(int status,T data) {
		this.status=status;
		this.data=data;
	}
	private ServerResponse(int status,String msg,T data) {
		this.status=status;
		this.msg=msg;
		this.data=data;
	}
	private ServerResponse(int status,String msg) {
		this.status=status;
		this.msg=msg;
	}
	
	//提供对外的方法  判断是不是 相等 成功的   序列化 后 不带这个 
	@JsonIgnore 
	public boolean isSuccess() {
		return this.status == ResponseCode.SUCCESS.getCode();
	}
	
	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}
	
	// 创建这个对象 不需要 参数 返回 成功的 状态
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    
    //创建成功的  返回 返回 提示消息
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    
    // 创建成功的  返回数据
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    //创建成功的 相应 返回 消息 和数据
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    //创建一个失败的 响应
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    //创建一个失败的 响应 和 提示
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    //创建一个失败的 响应  错误码 和提示消息
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
    
}
