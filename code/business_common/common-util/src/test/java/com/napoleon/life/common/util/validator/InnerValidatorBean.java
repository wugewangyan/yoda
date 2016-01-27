package com.napoleon.life.common.util.validator;

public class InnerValidatorBean {

	/**
     * 业务线投资订单号
     */
	@Signable
    @Validator(desc = "可以为空Parent", nullable = true, minLength = 2, maxLength = 10)
    private String nullableParent;

    /**
     * 投资下单时间
     */
	@Signable
    @Validator(desc = "日期格式Parent", isDate = true, dateFormat = "yyyy/MM/dd")
    private String investTimeParent;

    /**
     * 投资金额
     */
	@Signable
    @Validator(desc = "金额格式Parent", isCurrency = true, minValue = 10)
    private String currencyParent;

    /**
     * 支付金额
     */
	@Signable
    @Validator(desc = "支付金额Parent", isInteger = true, minValue = 100, maxValue = 1000)
    private String formatIntegerParent;

    /**
     * 投资协议号
     */
	@Signable
    private String investProtocolParent;

    /**
     * 用户id
     */
    @Validator(desc = "浮点型格式Parent", isDouble = true, minValue = 100, maxValue = 10000)
    private String formatDoubleParent;
    
    /**
     *  来源
     */
    @Signable
    @Validator(desc = "枚举类型Parent", enumScope = SourceTypeEnum.class)
    private String enumScopeParent;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "邮箱类型Parent", isEmail = true)
    private String emailParent;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "身份证类型Parent", isIdentity = true)
    private String identityParent;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "电话号码Parent", isPhone = true)
    private String phoneParent;
    
    /**
     * 业务类型[票支付申请、piaoDBConfirm]
     */
    @Signable
    @Validator(desc = "链接类型Parent", isUrl = true)
    private String urlParent;

	public String getNullableParent() {
		return nullableParent;
	}

	public void setNullableParent(String nullableParent) {
		this.nullableParent = nullableParent;
	}

	public String getInvestTimeParent() {
		return investTimeParent;
	}

	public void setInvestTimeParent(String investTimeParent) {
		this.investTimeParent = investTimeParent;
	}

	public String getCurrencyParent() {
		return currencyParent;
	}

	public void setCurrencyParent(String currencyParent) {
		this.currencyParent = currencyParent;
	}

	public String getFormatIntegerParent() {
		return formatIntegerParent;
	}

	public void setFormatIntegerParent(String formatIntegerParent) {
		this.formatIntegerParent = formatIntegerParent;
	}

	public String getInvestProtocolParent() {
		return investProtocolParent;
	}

	public void setInvestProtocolParent(String investProtocolParent) {
		this.investProtocolParent = investProtocolParent;
	}

	public String getFormatDoubleParent() {
		return formatDoubleParent;
	}

	public void setFormatDoubleParent(String formatDoubleParent) {
		this.formatDoubleParent = formatDoubleParent;
	}

	public String getEnumScopeParent() {
		return enumScopeParent;
	}

	public void setEnumScopeParent(String enumScopeParent) {
		this.enumScopeParent = enumScopeParent;
	}

	public String getEmailParent() {
		return emailParent;
	}

	public void setEmailParent(String emailParent) {
		this.emailParent = emailParent;
	}

	public String getIdentityParent() {
		return identityParent;
	}

	public void setIdentityParent(String identityParent) {
		this.identityParent = identityParent;
	}

	public String getPhoneParent() {
		return phoneParent;
	}

	public void setPhoneParent(String phoneParent) {
		this.phoneParent = phoneParent;
	}

	public String getUrlParent() {
		return urlParent;
	}

	public void setUrlParent(String urlParent) {
		this.urlParent = urlParent;
	}
}
