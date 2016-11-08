package com.napoleon.life.framework.enums;

public enum LoginSourceEnum {

	LOGIN_SOURCE_WLIFE("wonderful_life", "精彩生活");
	
	private String code;
	private String desc;
	
	private LoginSourceEnum(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static String toDesc(String code) {
		for (LoginSourceEnum category : LoginSourceEnum.values()) {
			if (category.getCode().equals(code)) {
				return category.getDesc();
			}
		}
		return null;
	}
	
	public static LoginSourceEnum toEnum(String code) {
        for (LoginSourceEnum category : LoginSourceEnum.values()) {
            if (category.getCode().equals(code)) {
                    return category;
            }
        }
        return null;
    }
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
