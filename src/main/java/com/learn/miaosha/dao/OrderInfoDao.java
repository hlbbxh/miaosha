package com.learn.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.learn.miaosha.entity.OrderInfo;

@Mapper
public interface OrderInfoDao {

	@Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
			+ "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate})")
	@SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id() as id")
	public long addOrderinfo(OrderInfo orderInfo);
	// before：在执行插入语句之前，我们设置为flase，既after（在插入这个语句之后，执行select last_insert_id()函数）   //mysql执行函数用select
	
	@Select("select * from order_info where id = #{orderId}")
	public OrderInfo getOrderById(@Param("orderId")long orderId);
}	
