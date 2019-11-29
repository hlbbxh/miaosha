package com.learn.mmall.dao;

import org.apache.ibatis.annotations.Mapper;

import com.learn.mmall.entity.Cart;
@Mapper
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}