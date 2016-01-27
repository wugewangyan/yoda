package com.napoleon.life.core.exception;

public enum LifeExceptionEnum {

	/**
	 * 000000~111111定义系统异常
	 */
	SUCCESS("000000", "SUCCESS", "成功"),
	SYSTEM_ERR("111111", "LIFE_ERR", "系统异常"),
	
	
	EMAIL_HAS_REGISTER("200000", "EMAIL_HAS_REGISTER", "该邮箱账号已经被注册"),
	ACCOUNT_NOT_EXIST("200001", "ACCOUNT_NOT_EXIST", "该用户不存在"),
	ACTIVATECODE_HAS_EXPIRED("200002", "ACTIVATECODE_HAS_EXPIRED", "激活码已经过期"),
	ACCOUNT_HAS_ACTIVATE("200003", "ACCOUNT_HAS_ACTIVATE", "账号已经激活，请登陆"),
	ACTIVATECODE_FAIL("200004", "ACTIVATECODE_FAIL", "账号激活失败，激活码不正确"),
	PASSWORD_WRONG("200005", "PASSWORD_WRONG", "密码错误"),
	ACCOUNT_NOT_ACTIVATE("200006", "ACCOUNT_NOT_ACTIVATE", "账号未激活"),
	ACCOUNT_STATUS_INVALID("200007", "ACCOUNT_STATUS_INVALID", "账号被冻结或注销"),
	RESET_EMAIL_HAS_EXPIRED("200008", "RESET_EMAIL_HAS_EXPIRED", "重置密码邮件已经过期"),
	SAFETY_CODE_FAIL("200009", "SAFETY_CODE_FAIL", "安全码不正确")
	
	;
	
	private final String code;
	private final String message;
	private final String chineseMessage;
	
	private LifeExceptionEnum(String code, String message, String chineseMessage){
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
