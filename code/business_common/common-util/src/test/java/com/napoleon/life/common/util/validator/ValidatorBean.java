package com.napoleon.life.common.util.validator;

import java.util.List;

public class ValidatorBean extends InnerValidatorBean{

	/**
     * 业务线投资订单号
     */
    @Validator(desc = "可以为空", nullable = true, minLength = 2, maxLength = 10)
    @Signable
    private String nullable;

    /**
     * 投资下单时间
     */
    @Signable
    @Validator(desc = "日期格式", isDate = true, dateFormat = "yyyy/MM/dd")
    private String investTime;

    /**
     * 投资金额
     */
    @Signable
    @Validator(desc = "金额格式", isCurrency = true, minValue = 10)
    private List<String> currency;

    /**
     * 支付金额
     */
    @Signable
    @Validator(desc = "支付金额", isInteger = true, minValue = 100, maxValue = 1000)
    private String formatInteger;

    /**
     * 投资协议号
     */
    private String investProtocol;

    /**
     * 用户id
     */
    @Signable
    @Validator(desc = "浮点型格式", isDouble = true, minValue = 100, maxValue = 10000)
    private String formatDouble;
    
    /**
     *  来源
     */
    @Signable
    @Validator(desc = "枚举类型", enumScope = SourceTypeEnum.class)
    private String enumScope;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "邮箱类型", isEmail = true)
    private String email;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "身份证类型", isIdentity = true)
    private String identity;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "电话号码", isPhone = true)
    private String phone;
    
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "链接类型", isUrl = true)
    private String url;

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	

	public List<String> getCurrency() {
		return currency;
	}

	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}

	public String getFormatInteger() {
		return formatInteger;
	}

	public void setFormatInteger(String formatInteger) {
		this.formatInteger = formatInteger;
	}

	public String getInvestProtocol() {
		return investProtocol;
	}

	public void setInvestProtocol(String investProtocol) {
		this.investProtocol = investProtocol;
	}

	public String getFormatDouble() {
		return formatDouble;
	}

	public void setFormatDouble(String formatDouble) {
		this.formatDouble = formatDouble;
	}

	public String getEnumScope() {
		return enumScope;
	}

	public void setEnumScope(String enumScope) {
		this.enumScope = enumScope;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
