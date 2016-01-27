package com.napoleon.life.core.enums;

public enum QuarterEnum {

	QUARTER_1("1,2,3", "第一季度"),
	QUARTER_2("4,5,6", "第一季度"),
	QUARTER_3("7,8,9", "第一季度"),
	QUARTER_4("10,11,12", "第一季度");
	
	private String code;
	private String desc;
	
	private QuarterEnum(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static String toDesc(String code) {
		for (QuarterEnum category : QuarterEnum.values()) {
			if (category.getCode().equals(code)) {
				return category.getDesc();
			}
		}
		return null;
	}
	
	public static QuarterEnum toEnum(String code) {
        for (QuarterEnum category : QuarterEnum.values()) {
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
