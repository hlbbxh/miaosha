package com.learn.mmall.service.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.mmall.common.Const;
import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.common.TokenCache;
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
    
    public ServerResponse<String> selectQuestion(String username) {
    	int checkUsername = userMapper.checkUsername(username);
    	if(checkUsername<=0) {
    		return ServerResponse.createByErrorMessage("用户不存在");
    	}
    	String selectQuestionByUserName = userMapper.selectQuestionByUserName(username);
    	if(StringUtils.isNotBlank(selectQuestionByUserName)) {
    		return ServerResponse.createBySuccess(selectQuestionByUserName);
    	}
    	return ServerResponse.createByErrorMessage("找回密码的问题为空，无法找回");
    }
    
    
    public ServerResponse<String> checkanswer(String username,String question,String answer){
    	int checkanswer = userMapper.checkanswer(username, question, answer);
    	if(checkanswer>0) {
    		//正确
    		String token = UUID.randomUUID().toString();//token
    		TokenCache.setKey(TokenCache.TOKEN_PREFIX+username, token);//放入缓存
    		return ServerResponse.createBySuccess(token);
    	}
    	return ServerResponse.createByErrorMessage("密保验证失败");
    }
    
    public ServerResponse<String> restPassword(String username,String newpassword,String forgetToken){
    	if(StringUtils.isBlank(forgetToken)) {
    		return ServerResponse.createByErrorMessage("token为空");
    	}
    	int checkUsername = userMapper.checkUsername(username);
    	if(checkUsername<=0) {
    		return ServerResponse.createByErrorMessage("用户不存在");
    	}
    	String getToken = TokenCache.getkey(TokenCache.TOKEN_PREFIX+username);
    	if(StringUtils.isBlank(getToken)) {
    		return ServerResponse.createByErrorMessage("token已经失效，请重新获取");
    	}
    	if(StringUtils.equals(forgetToken, getToken)) {//success true 相等的话
    		String md5passwordString = MD5Util.MD5EncodeUtf8(newpassword);
    		int updatePasswordByusername = userMapper.updatePasswordByusername(username, md5passwordString);
    		if(updatePasswordByusername>0) {
    			return ServerResponse.createBySuccess("修改密码成功");
    		}
    	}else {
    		return ServerResponse.createByErrorMessage("token错误，请重新获取");
    	}
    	return ServerResponse.createByErrorMessage("修改密码失败");
    }
    
    public ServerResponse<String> resultPassword(String passwordOld,String passwordNew,User user){
    	int checnPassword = userMapper.checnPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());//防止横向越权问题
    	if(checnPassword==0) {
    		return ServerResponse.createByErrorMessage("旧密码错误");
    	}
    	user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
    	int updateByPrimaryKeySelective = userMapper.updateByPrimaryKeySelective(user);//选择性的更新
    	if(updateByPrimaryKeySelective>0) {
    		return ServerResponse.createBySuccessMessage("密码修改成功");
    	}
    	return ServerResponse.createByErrorMessage("密码更新失败");
    }
    
   public ServerResponse<User> updateInfo(User user){
	   int checkEmailUserByUserid = userMapper.checkEmailUserByUserid(user.getEmail(),user.getId());
	   if(checkEmailUserByUserid > 0) {
		   return ServerResponse.createByErrorMessage("email已经存在了，换一个邮箱");
	   }
	   User updateuserUser = new User();
	   updateuserUser.setId(user.getId());
	   updateuserUser.setEmail(user.getEmail());
	   updateuserUser.setPhone(user.getPhone());
	   updateuserUser.setQuestion(user.getQuestion());
	   updateuserUser.setAnswer(user.getAnswer());
	   int updateByPrimaryKeySelective = userMapper.updateByPrimaryKeySelective(updateuserUser);
	   if(updateByPrimaryKeySelective>0) {
		   return ServerResponse.createBySuccessMessage("更新个人信息成功");
	   }
	   return ServerResponse.createByErrorMessage("更新失败");
   }
   
   public ServerResponse<User> getuserinfo(Integer userid){
	   User userinfo = userMapper.selectByPrimaryKey(userid);
	   if(null==userinfo) {
		   return ServerResponse.createByErrorMessage("获取用户信息失败");
	   }
	   userinfo.setPassword("");//记得密码要制空
	   return ServerResponse.createBySuccess(userinfo);
   }
   
   //检验用户是否是管理员 
   public ServerResponse checkAdmin(User user) {
	   if(null != user && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
		   return ServerResponse.createBySuccess();
	   }
	   return ServerResponse.createByError();
   }
   
}
