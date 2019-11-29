package com.learn.mmall.dao;

import org.apache.ibatis.annotations.Mapper;

import com.learn.mmall.entity.Product;
@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}