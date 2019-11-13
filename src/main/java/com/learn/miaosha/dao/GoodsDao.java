package com.learn.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.learn.miaosha.entity.MiaoshaGoods;
import com.learn.miaosha.entity.MiaoshaUser;
import com.learn.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {
	
	/**
	 * @return 查询所有
	 */
	@Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();//注意注解
	
	/**
	 * @param goodsId 查询一个 
	 * @return
	 */
	@Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
	public GoodsVo getgoodsvo(@Param("goodsId")Long goodsId);
	
	@Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
	public int updateMiaoshaStock(MiaoshaGoods maisMiaoshaGoods);
}
