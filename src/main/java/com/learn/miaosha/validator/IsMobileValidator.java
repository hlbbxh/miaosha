package com.learn.miaosha.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.learn.miaosha.utils.ValidatorUtil;
/**
 * @author DELL  jsr303 》》》第四步 在这里进行校验
 *
 */
public class IsMobileValidator implements ConstraintValidator<Mobile,String>{
	//实现 ConstraintValidator 接口 
	// 注意是 import javax.validation.ConstraintValidator 接口
	
	private boolean required = false;
	
	// 构造方法 是否为空
	@Override
	public void initialize(Mobile constraintAnnotation) {
		required = constraintAnnotation.required();//是否为空
	}
	
	//在这里进行检验和判断
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {//value 就是注解的值
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}
	
}
