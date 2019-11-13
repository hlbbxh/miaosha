package com.learn.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.learn.miaosha.entity.MiaoshaOrder;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.vo.GoodsVo;

@Mapper
public interface MiaoshaOrderDao {
	
	/**
	 * @param userId 用户 
	 * @param goodsId 商品 判断同一个用户是否秒杀了相同的商品
	 * @return
	 */
	@Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
	public MiaoshaOrder getOrderByUserAndGoodsid(@Param("userId")long userId,@Param("goodsId")long goodsId);//注意注解

	/**
	 * @param miaoshaOrder 插入 秒杀信息
	 * @return
	 */
	@Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
	public int createMiaosOrder(MiaoshaOrder miaoshaOrder);
}
