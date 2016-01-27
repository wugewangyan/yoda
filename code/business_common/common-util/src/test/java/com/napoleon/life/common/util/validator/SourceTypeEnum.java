package com.napoleon.life.common.util.validator;

/**
 * 终端类型 Enum
 * @author Jasper Guo
 *
 */
public enum SourceTypeEnum {
    WEB(1, "web"), 
    POS(2, "pos");
    
    private Integer code;
    private String desc;

    private SourceTypeEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
    }

    public static SourceTypeEnum toEnum(Integer code) {
            for (SourceTypeEnum category : SourceTypeEnum.values()) {
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
