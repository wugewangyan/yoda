package com.napoleon.life.common.util.exception;

public enum UtilExceptionEnum {

	SUCCESS("000000", "SUCCESS", "成功"),
	COMMON_UTIL_ERR("111111", "COMMON_UTIL_ERR", "未知异常");
	
	private final String code;
	private final String message;
	private final String chineseMessage;
	
	private UtilExceptionEnum(String code, String message, String chineseMessage){
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
