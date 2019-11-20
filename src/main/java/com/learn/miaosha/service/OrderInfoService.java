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
		orderInfoDao.addOrderinfo(orderInfo);//插入普通订单  //这里有一个1问题 每 次返回的 都是一 mmp
		// 注意 插入成功 会吧id直接放入 orderInfo  从 orderInfo 里面获取   /// bug 解决 
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goodsVo.getId());//商品id
		miaoshaOrder.setOrderId(orderInfo.getId());//订单id
		miaoshaOrder.setUserId(miaoshaUser.getId());
		int resultcount = miaoshaOrderService.createMiaosOrder(miaoshaOrder);
		
		return orderInfo;
	}
	
	public OrderInfo getOrderById(long orderId) {
		return orderInfoDao.getOrderById(orderId);
	}
}
