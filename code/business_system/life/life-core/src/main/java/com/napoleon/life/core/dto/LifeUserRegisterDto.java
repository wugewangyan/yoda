package com.napoleon.life.core.dto;

import com.napoleon.life.common.util.validator.Validator;

public class LifeUserRegisterDto {

	/**
	 * 用户姓名
	 */
	@Validator(desc = "用户姓名", nullable = false, maxLength = 32)
	private String userName;

	/**
	 * 邮箱账号
	 */
	@Validator(desc = "邮箱账号", isEmail = true, nullable = false)
	private String email;

	/**
	 * 密码，密文
	 */
	@Validator(desc = "密码", minLength = 8, maxLength = 20, nullable = false)
	private String password;

	/**
	 * 确认密码
	 */
	@Validator(desc = "确认密码", minLength = 8, maxLength = 20, nullable = false, dependesOn = "equalsPassword")
	private String confirmPassword;

	/**
	 * 验证码
	 */
	@Validator(desc = "验证码", nullable = false, dependesOn = "validateIdentifyCode")
	private String identifyCode;
	
	
	private String sessionScopeIdentifyCode;

	public boolean equalsPassword() {
		return this.confirmPassword != null && this.confirmPassword.equals(this.password);
	}
	
	public boolean validateIdentifyCode(){
		return this.identifyCode != null && this.identifyCode.equals(sessionScopeIdentifyCode);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getSessionScopeIdentifyCode() {
		return sessionScopeIdentifyCode;
	}

	public void setSessionScopeIdentifyCode(String sessionScopeIdentifyCode) {
		this.sessionScopeIdentifyCode = sessionScopeIdentifyCode;
	}
}
