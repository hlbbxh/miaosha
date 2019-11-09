package com.learn.miaosha.utils;

import java.util.UUID;
 
/**
 * @author DELL  uuid工具类
 *
 */
public class UUIDUtil {
	public static String makeUuid() {
		return UUID.randomUUID().toString().replace("-","");
	}
	public static void main(String[] args) {
		System.out.println(makeUuid());
	}
}
