package com.learn.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.miaosha.dao.OrderInfoDao;
import com.learn.miaosha.entity.MiaoshaOrder;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.entity.OrderInfo;
import com.learn.miaosha.vo.GoodsVo;

@Service
public class OrderInfoService {
	
	@Autowired
	private OrderInfoDao orderInfoDao;
	
	@Autowired
	private MiaoshaOrderService miaoshaOrderService;

	
	/**
	 * @param miaoshaUser
	 * @param goodsVo 产生订单的方法 ----------------->>>>  两个方法的sql语句没写 
	 * @return
	 */
	@Transactional  //注意这里也是 事务 
	public OrderInfo createOrder(MiaoshaUser miaoshaUser,GoodsVo goodsVo) {
		
		OrderInfo orderInfo = new OrderInfo();//订单
		orderInfo.setCreateDate(new Date());//时间
		orderInfo.setDeliveryAddrId(0L);//收货地址 先不弄了
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goodsVo.getId());//商品
		orderInfo.setGoodsName(goodsVo.getGoodsName());
		orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());//秒杀价格
		orderInfo.setOrderChannel(1);//渠道 
		orderInfo.setStatus(0);
		orderInfo.setUserId(miaoshaUser.getId());//用户
		long orderinfoId = orderInfoDao.addOrderinfo(orderInfo);//插入普通订单  
		
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goodsVo.getId());//商品id
		miaoshaOrder.setOrderId(orderinfoId);//订单id
		miaoshaOrder.setUserId(miaoshaUser.getId());
		int resultcount = miaoshaOrderService.createMiaosOrder(miaoshaOrder);
		
		return orderInfo;
	}
	
}
