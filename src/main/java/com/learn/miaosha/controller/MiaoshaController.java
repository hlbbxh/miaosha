package com.learn.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learn.miaosha.entity.MiaoshaOrder;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.entity.OrderInfo;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.service.GoodsService;
import com.learn.miaosha.service.MiaoshaOrderService;
import com.learn.miaosha.service.MiaoshaService;
import com.learn.miaosha.vo.GoodsVo;
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	
	@Autowired
	private GoodsService goodsService;//商品
	
	@Autowired
	private RedisService redisService;//redis
	
	@Autowired
	private MiaoshaOrderService miaoshaOrderService;//订单
	
	@Autowired
	private MiaoshaService miaoshaService;//秒杀核心业务
	/**
	 * success 调用
	 * @return
	 */
 	@RequestMapping("/do_miaosha")
    public String goodslist(HttpServletResponse response,Model model,MiaoshaUser miaoshaUser,@RequestParam("goodsId")long goodsId) {
 		if(null==miaoshaUser) {//是否登录
 			return "login";
 		}
 		GoodsVo miaoshaGoods = goodsService.getgoodsvobyId(goodsId);//获取秒杀商品
 		Integer stockCount = miaoshaGoods.getStockCount();//获取库存
 		//1.判断商品库存
 		if(stockCount <= 0) {
 			model.addAttribute("errorMsg", ErrorCodeMsg.STOCK_BUZU.getMsg());//没库存了
 			return "miaosha_fail";
 		}
 		//2.判断用户是否秒杀了相同的商品 
 		MiaoshaOrder orderByUserAndGoodsid = miaoshaOrderService.getOrderByUserAndGoodsid(miaoshaUser.getId(),goodsId);
 		if(null!=orderByUserAndGoodsid) {
 			model.addAttribute("errorMsg", ErrorCodeMsg.USER_EXIT.getMsg());//没库存了
 			return "miaosha_fail";
 		}
 		//3.减库存 下订单 写入秒杀订单   原子性的 事务
 		OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser,miaoshaGoods);
 		
 		model.addAttribute("orderInfo",orderInfo);//订单
 		model.addAttribute("miaoshaGoods",miaoshaGoods);//商品
 		model.addAttribute("miaoshaUser",miaoshaUser);//用户
 		return "order_detail";
    }

 	
}
