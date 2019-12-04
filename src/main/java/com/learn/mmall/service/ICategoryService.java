package com.learn.mmall.service;

import com.learn.mmall.common.ServerResponse;

public interface ICategoryService{
	ServerResponse addCategory(String categoryName,Integer parentid);
	ServerResponse updateCategoryName(Integer categoryId,String categoryName);
}
