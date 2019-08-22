package com.learn.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.learn.miaosha.entity.User;

@Mapper
public interface UserDao {
	
	@Select("select * from user where user_id = #{id}")
	public User getById(@Param("id")int id);

	@Insert("insert into user(user_id, user_name)values(#{id}, #{name})")
	public int insert(User user);
}
