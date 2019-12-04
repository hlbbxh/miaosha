package com.learn.mmall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.learn.mmall.entity.User;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    //查询是否存在名称
    int checkUsername(String username);
    
    //登录 
    User selectlogin(@Param("username")String username,@Param("password")String password);
    
    //查询email是否存在
    int checkUserEmail(String email);
    
    String selectQuestionByUserName(@Param("username")String username);
    
    int checkanswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);

    int updatePasswordByusername(@Param("username")String username,@Param("password")String password);
    
    int checnPassword(@Param("password")String password,@Param("userid")Integer userid);
    
    int checkEmailUserByUserid(@Param("email")String email,@Param("userid")Integer userid);
}