package com.napoleon.life.core.util;

public class Constants {

	/**
	 * 生成用户名的前缀
	 */
	public static final String USER_NO = "CREATE_USER";
	
	/**
	 * cookie 名称
	 */
	public static final String COOKIE_NAME = "COM.NAPOLEON.LIFE";
	
	/**
	 * 保存用户信息的session名称
	 */
	public static final String USER_SESSION_NAME = "LIFE_USER";
	

	// 设置cookie有效期是两个星期，根据需要自定义
	public static final long COOKIE_MAX_AGE = 60 * 60 * 24 * 7 * 2;
	
	/**
	 * 加密COOKIE时的secure_key
	 */
	public static final String COOKIE_SECURE_KEY = "F697467B14B076F2";
}
