package com.learn.mmall.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.mmall.common.Const;
import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.dao.UserMapper;
import com.learn.mmall.entity.User;
import com.learn.mmall.service.IUserService;
import com.learn.mmall.utils.MD5Util;
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	/**
	 *by：登录
	 */
	@Override
	public ServerResponse<User> login(String username, String password) {
		int checkUsername = userMapper.checkUsername(username);
		if(checkUsername==0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		String md5EncodeUtf8 = MD5Util.MD5EncodeUtf8(password);
		User selectlogin = userMapper.selectlogin(username, md5EncodeUtf8);
		if(null==selectlogin) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		selectlogin.setPassword(StringUtils.EMPTY);//制空密码
		return ServerResponse.createBySuccess("登录成功",selectlogin);
	}
	
	
	/**
	 *by:注册
	 */
	public ServerResponse<String> register(User user) {
		ServerResponse<String> checkValid = this.checkVaild(user.getUsername(),Const.USERNAME);//name
		if(!checkValid.isSuccess()) {
			return checkValid;
		}
		checkValid = this.checkVaild(user.getEmail(),Const.EMAIL);//email
		if(!checkValid.isSuccess()) {
			return checkValid;
		}
		//密码 md5加密
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		user.setRole(Const.Role.ROLE_CUSTOMER);//普通用户
		int insert = userMapper.insert(user);
		if(insert==0) {
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}
	
    /**
     * by：校验用户名和邮箱
     * @param str
     * @param type
     * @return
     */
    public ServerResponse<String> checkVaild(String str,String type){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkUserEmail(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
}
