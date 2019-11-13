package com.learn.miaosha.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.miaosha.dao.MiaoshaOrderDao;
import com.learn.miaosha.entity.MiaoshaOrder;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.entity.OrderInfo;
import com.learn.miaosha.vo.GoodsVo;

@Service
public class MiaoshaOrderService {
	
	@Autowired
	private MiaoshaOrderDao miaoshaOrderDao;
	

	/**
	 * @param userId
	 * @param goodsId 判断同一个用户是否秒杀相同的商品
	 * @return
	 */
	public MiaoshaOrder getOrderByUserAndGoodsid(long userId,long goodsId){
		MiaoshaOrder orderByUserAndGoodsid = miaoshaOrderDao.getOrderByUserAndGoodsid(userId, goodsId);
		return orderByUserAndGoodsid;
	}
	
	/**
	 * @param miaoshaOrder 创建秒杀订单
	 * @return
	 */
	public Integer createMiaosOrder(MiaoshaOrder miaoshaOrder) {
		int resultcount = miaoshaOrderDao.createMiaosOrder(miaoshaOrder);
		return resultcount;
	}
	
}
