package com.learn.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * @author DELL
 * a 两次 MD5 加密 检验工具类
 */
public class MD5Util {
	
	//md5一次的
	public static String md5(String src) {
		//commons 包下的md5
		return DigestUtils.md5Hex(src);
	}
	
	//盐
	private static final String salt = "1a2b3c4d";
	
	//第0个字符 加第2个字符 
	public static String inputPassToFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	//随机的字符串formPass  盐是随机的 salt 保存到数据库的
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	//解析的 inputPass 输入的密码 和数据库的盐 
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);//第一次的固定的
		String dbPass = formPassToDBPass(formPass, saltDB);//第二次的  随机的salt
		return dbPass;//最终密码 
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
//		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
//		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449
	}
	
}
