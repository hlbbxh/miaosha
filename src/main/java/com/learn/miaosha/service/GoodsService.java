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

}
