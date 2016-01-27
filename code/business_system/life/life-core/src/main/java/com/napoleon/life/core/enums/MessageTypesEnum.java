package com.napoleon.life.core.enums;

public enum MessageTypesEnum {

	REGISTER_SUCCESS(0, "注册成功"),
	REGISTER_FAIL(1, "注册失败"),
	VALIDATE_SUCCESS(2, "激活成功"),
	VALIDATE_FAIL(3, "激活失败"),
	EMAIL_SEND_SUCCESS(4, "邮件发送成功"),
	EMAIL_SEND_FAIL(5, "邮件发送失败"),
	LOGIN_FAIL(6, "登录失败"),
	FIND_PASSWORD_FAIL(7, "找回密码失败"),
	RESET_PASSWORD_FAIL(8, "密码重置失败"),
	RESET_PASSWORD_SUCCESS(9, "密码重置成功");

	private Integer code;
	private String desc;
	
	private MessageTypesEnum(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static String toDesc(Integer code) {
		for (MessageTypesEnum category : MessageTypesEnum.values()) {
			if (category.getCode().equals(code)) {
				return category.getDesc();
			}
		}
		return null;
	}
	
	public static MessageTypesEnum toEnum(Integer code) {
        for (MessageTypesEnum category : MessageTypesEnum.values()) {
            if (category.getCode().equals(code)) {
                    return category;
            }
        }
        return null;
    }
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
