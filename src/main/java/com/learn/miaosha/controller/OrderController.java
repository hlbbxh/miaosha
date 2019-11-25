package com.learn.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.entity.OrderInfo;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.result.Result;
import com.learn.miaosha.service.GoodsService;
import com.learn.miaosha.service.MiaoshaUserService;
import com.learn.miaosha.service.OrderInfoService;
import com.learn.miaosha.vo.GoodsVo;
import com.learn.miaosha.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderInfoService OrderInfoService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model,MiaoshaUser user,
    		@RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(ErrorCodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = OrderInfoService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(ErrorCodeMsg.ORDER_NULL);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getgoodsvobyId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
