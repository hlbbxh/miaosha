package com.learn.miaosha.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.miaosha.core.Content;
import com.learn.miaosha.dao.GoodsDao;
import com.learn.miaosha.dao.MiaoshaUserDao;
import com.learn.miaosha.entity.Goods;
import com.learn.miaosha.entity.MiaoshaGoods;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.exception.GlobalException;
import com.learn.miaosha.redis.MiaoshaUserKey;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.result.ErrorCodeMsg;
import com.learn.miaosha.utils.MD5Util;
import com.learn.miaosha.utils.UUIDUtil;
import com.learn.miaosha.vo.GoodsVo;
import com.learn.miaosha.vo.LoginVo;

@Service
public class GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;

	public List<GoodsVo> listGoodsvo(){
		List<GoodsVo> listGoodsVo = goodsDao.listGoodsVo();
		return listGoodsVo;
	}
	
	public GoodsVo getgoodsvobyId(long goodsId) {
		GoodsVo getgoodsvo = goodsDao.getgoodsvo(goodsId);
		return getgoodsvo;
	}

	/**
	 * @return 下单更新库存
	 */
	public int updateStock(GoodsVo goodsVo) {
		MiaoshaGoods miaoshaGoods = new MiaoshaGoods();//
		miaoshaGoods.setGoodsId(goodsVo.getId());
		//miaoshaGoods.setStockCount(goodsVo.getStockCount()-1);//减少库存  也可以在sql里面进行减少 
		int updateMiaoshaStock = goodsDao.updateMiaoshaStock(miaoshaGoods);
		return updateMiaoshaStock;
	}
	
}
