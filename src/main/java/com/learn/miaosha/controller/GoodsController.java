package com.learn.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.redis.GoodsKey;
import com.learn.miaosha.redis.RedisService;
import com.learn.miaosha.service.GoodsService;
import com.learn.miaosha.vo.GoodsVo;
@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver; //html缓存 需要用的
	/**
	 * success 调用
	 * @return
	 */
 	@RequestMapping("/goodslist")
    public String goodslist(HttpServletResponse response,Model model,MiaoshaUser miaoshaUser) {
		//@CookieValue(value = Content.COOK_NAME_TOCK,required=false) String cookieToken,
		//@RequestParam(value =Content.COOK_NAME_TOCK,required=false) String parmCookieToken,
 		//有些手机端 不会放在cook 会放在参数里面  可能为空
		/*
		 * if(null==cookieToken && null==parmCookieToken) {//都是空的就去登录 return "login"; }
		 * String token =
		 * StringUtils.isEmpty(parmCookieToken)?cookieToken:parmCookieToken;//三目运算符
		 * MiaoshaUser miaoshaUser =
		 * miaoshaUserService.getByToken(response,token);//从缓存里面获取
		 */ 		//去往商品列表
 		//------------------------------------------------>删除的代码是因为每次都要获取 cookieToken parmCookieToken 就很繁琐
 		// 所以可以选择直接吧user对象直接注入到里面来
 		// 加入了视图解析器
 		//if(null==miaoshaUser) {//没有的话就去登陆啦
 		//	return "login";
 		//}
 		List<GoodsVo> listGoodsvo = goodsService.listGoodsvo();
 		model.addAttribute("listGoodsvo", listGoodsvo); 
 		model.addAttribute("miaoshaUser", miaoshaUser); 
 		return "goods_list";
    }
 	@RequestMapping("/to_detail/{goodsId}") //snowflake算法-->了解一下 
 	public String goodsDetil(Model model,MiaoshaUser miaoshaUser,@PathVariable("goodsId")long goodsId) {
 		GoodsVo goodvo = goodsService.getgoodsvobyId(goodsId);
 		
 		long starttime = goodvo.getStartDate().getTime();//开始时间
 		long endtime = goodvo.getEndDate().getTime();//结束时间
 		long nowtime = System.currentTimeMillis();//现在时间
 		
 		int remainSeconds = 0; //还要多久开始 倒计时
 		int miaoshaStatus = 0;//秒杀状态
 		
 		if(nowtime<starttime) {//开始时间小于当前时间 没开始
 			miaoshaStatus = 0;
 			remainSeconds = (int) ((starttime - nowtime)/1000);//还有多久开始 除 1000 是毫秒
 		}else if(nowtime>endtime) {//结束了
 			miaoshaStatus = 2;
 			remainSeconds = -1;
 		}else{//进行中
 			miaoshaStatus = 1;
 			remainSeconds = 0;//结束了 就没有这个时间了 
 		}
 		
 		model.addAttribute("remainSeconds", remainSeconds); 
 		model.addAttribute("miaoshaStatus", miaoshaStatus); 
 		
 		model.addAttribute("miaoshaUser", miaoshaUser); 
 		model.addAttribute("goodvo",goodvo);
 		return "goods_detail";
 	}
 	
 	@RequestMapping(value = "/goodslist_catch",produces = "text/html")//这个 方式
 	@ResponseBody
    public String goodslist_catch(HttpServletRequest request,HttpServletResponse response,Model model,MiaoshaUser miaoshaUser) {

 		String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
 		if(!StringUtils.isEmpty(html)) {
 			return html;//直接返回
 		}
 		
 		List<GoodsVo> listGoodsvo = goodsService.listGoodsvo();
 		// 在渲染之前放入
 		model.addAttribute("listGoodsvo", listGoodsvo); 
 		model.addAttribute("miaoshaUser", miaoshaUser); 
 		//spring 2 使用的是 springwebcontext  现在使用的 是 WebContext   这个 还需要 applicationContext  还需要 model 视图 可以直接使用 asmap
 		WebContext webContext = new WebContext(request, response, request.getServletContext(),request.getLocale(),model.asMap());//需要 ServletContext 可以直接从request获取
 		// webContext 也还是这几个参数啦  request response request.getServletContext() request.getLocale()  model.asMap()
 		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);//两个参数 goods_list   放入 webContext
 		if(!StringUtils.isEmpty(html)) {// 不是空 放在 缓存
 			redisService.set(GoodsKey.getGoodsList,"",html);//存入
 		}
 		return html;
 	}
}
