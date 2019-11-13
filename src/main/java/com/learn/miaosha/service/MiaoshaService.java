package com.learn.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.miaosha.dao.GoodsDao;
import com.learn.miaosha.dao.MiaoshaOrderDao;
import com.learn.miaosha.entity.Goods;
import com.learn.miaosha.entity.MiaoshaGoods;
import com.learn.miaosha.entity.MiaoshaOrder;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.entity.OrderInfo;
import com.learn.miaosha.vo.GoodsVo;

@Service
public class MiaoshaService {
	
	@Autowired
	private MiaoshaOrderDao miaoshaOrderDao;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	/**
	 * @param miaoshaUser
	 * @param goods 秒杀的核心业务
	 * @return
	 */
	@Transactional //事务
	public OrderInfo miaosha(MiaoshaUser miaoshaUser,GoodsVo goodsVo) {
		//1.减少库存
		int updateStock = goodsService.updateStock(goodsVo);//更新库存
		
		//2.产生订单  这里也是事务 
		OrderInfo createOrder = orderInfoService.createOrder(miaoshaUser, goodsVo);
		
		return createOrder;
	
	}
	 
}
