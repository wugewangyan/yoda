package com.napoleon.life.exception;

public enum CommonResultCode {

	/**
	 * 000000~111111定义系统异常
	 */
	SUCCESS("000000", "SUCCESS", "成功"),
	COMMON_UTIL_ERR("000001", "COMMON_UTIL_ERR", "未知异常"),
	SYSTEM_ERR("111111", "LIFE_ERR", "系统异常"),
	
	
	
	/**
	 * 200000~111111 common-user 异常枚举定义
	 */
	PHONE_NUMBER_HAS_REGISTER("200000", "PHONE_NUMBER_HAS_REGISTER", "该手机号码已经被注册，请登陆"),
	PHONE_CODE_EXPIRED("2000001", "PHONE_CODE_EXPIRED", "手机验证码已经失效,请重新获取"),
	PHONE_CODE_WRONG("200002", "PHONE_CODE_WRONG", "手机验证码错误"),
	ACCOUNT_NOT_EXIST("200003", "ACCOUNT_NOT_EXIST", "用户不存在，请注册"),
	PASSWORD_WRONG("200004", "PASSWORD_WRONG", "密码错误"),
	ACCOUNT_NOT_ACTIVATE("200005", "ACCOUNT_NOT_ACTIVATE", "账号未激活,请激活账户后重新登陆"),
	ACCOUNT_STATUS_INVALID("200006", "ACCOUNT_STATUS_INVALID", "账号被冻结或注销"),
	REJECT_SOURCE_LOGIN("200007", "REJECT_SOURCE_LOGIN", "未知的登陆来源"),
	
	ACTIVATECODE_HAS_EXPIRED("200002", "ACTIVATECODE_HAS_EXPIRED", "激活码已经过期"),
	ACCOUNT_HAS_ACTIVATE("200003", "ACCOUNT_HAS_ACTIVATE", "账号已经激活，请登陆"),
	ACTIVATECODE_FAIL("200004", "ACTIVATECODE_FAIL", "账号激活失败，激活码不正确"),
	
	
	RESET_EMAIL_HAS_EXPIRED("200008", "RESET_EMAIL_HAS_EXPIRED", "重置密码邮件已经过期"),
	SAFETY_CODE_FAIL("200009", "SAFETY_CODE_FAIL", "安全码不正确"),
	
	
	
	
	
	
	;
	
	private final String code;
	private final String message;
	private final String chineseMessage;
	
	private CommonResultCode(String code, String message, String chineseMessage){
		this.code = code;
		this.message = message;
		this.chineseMessage = chineseMessage;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getChineseMessage() {
		return chineseMessage;
	}
}
