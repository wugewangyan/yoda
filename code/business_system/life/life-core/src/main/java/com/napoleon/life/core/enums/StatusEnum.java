package com.napoleon.life.core.enums;

public enum StatusEnum {

	STATUS_PROCESSING(0, "已受理"),
	STATUS_SUCCESS(1, "成功"),
	STATUS_FAIL(2, "失败");

	private Integer code;
	private String desc;
	
	private StatusEnum(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static String toDesc(String code) {
		for (StatusEnum category : StatusEnum.values()) {
			if (category.getCode().equals(code)) {
				return category.getDesc();
			}
		}
		return null;
	}
	
	public static StatusEnum toEnum(String code) {
        for (StatusEnum category : StatusEnum.values()) {
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
