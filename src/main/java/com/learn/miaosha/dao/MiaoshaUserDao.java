package com.learn.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.learn.miaosha.entity.MiaoshaUser;

@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where nickname = #{phone}")
	public MiaoshaUser getById(@Param("phone")long phone);//注意注解
}
