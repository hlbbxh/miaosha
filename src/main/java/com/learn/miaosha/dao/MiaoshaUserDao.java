package com.learn.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.learn.miaosha.entity.MiaoshaUser;

@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where nickname = #{phone}")
	public MiaoshaUser getById(@Param("phone")long phone);//注意注解

	@Update("update miaosha_user set password = #{password} where id = #{id}") //注意 不更新 不传递
	public void updateMiaoshaUser(MiaoshaUser miaoshaUser);
}
