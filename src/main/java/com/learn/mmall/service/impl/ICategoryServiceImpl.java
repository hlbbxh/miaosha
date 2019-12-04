package com.learn.mmall.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.mmall.common.ServerResponse;
import com.learn.mmall.dao.CategoryMapper;
import com.learn.mmall.entity.Category;
import com.learn.mmall.service.ICategoryService;
@Service
public class ICategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	public ServerResponse addCategory(String categoryName,Integer parentid) {
		if(null==parentid || null==categoryName) {
			ServerResponse.createByErrorMessage("参数错误");
		}
		Category category = new Category();
		category.setName(categoryName);
		category.setParentId(parentid);
		category.setStatus(true);
		int insert = categoryMapper.insert(category);
		if( insert > 0) {
			return ServerResponse.createBySuccessMessage("添加成功");
		}
		return ServerResponse.createBySuccessMessage("添加失败");
	}
	
    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }
}
