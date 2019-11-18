package com.learn.miaosha.vo;

import com.learn.miaosha.entity.MiaoshaUser;

/**
 * @author DELL 页面静态化的 需要传递的 对象
 *
 */
public class GoodsDetailVo {
	private Integer miaoshaStatus;//秒杀状态
	private Integer remainSeconds; //还要多久开始 倒计时
	private GoodsVo goodsVo;//商品
	private MiaoshaUser miaoshaUser;//用户
	
	public Integer getMiaoshaStatus() {
		return miaoshaStatus;
	}
	public void setMiaoshaStatus(Integer miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}
	public Integer getRemainSeconds() {
		return remainSeconds;
	}
	public void setRemainSeconds(Integer remainSeconds) {
		this.remainSeconds = remainSeconds;
	}
	public GoodsVo getGoodsVo() {
		return goodsVo;
	}
	public void setGoodsVo(GoodsVo goodsVo) {
		this.goodsVo = goodsVo;
	}
	
	public MiaoshaUser getMiaoshaUser() {
		return miaoshaUser;
	}
	public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
		this.miaoshaUser = miaoshaUser;
	}
	@Override
	public String toString() {
		return "GoodsDetailVo [miaoshaStatus=" + miaoshaStatus + ", remainSeconds=" + remainSeconds + ", goodsVo="
				+ goodsVo + "]";
	}
}
