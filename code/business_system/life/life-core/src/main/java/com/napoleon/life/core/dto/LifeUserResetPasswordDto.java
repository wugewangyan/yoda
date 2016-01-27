package com.napoleon.life.core.dto;

import com.napoleon.life.common.util.validator.Validator;

public class LifeUserResetPasswordDto {

	/**
	 * 邮箱账号
	 */
	@Validator(desc = "邮箱账号", isEmail = true, nullable = false)
	private String email;

	/**
	 * 密码
	 */
	@Validator(desc = "密码", minLength = 8, maxLength = 20, nullable = false)
	private String password;

	/**
	 * 确认密码
	 */
	@Validator(desc = "确认密码", minLength = 8, maxLength = 20, nullable = false, dependesOn = "equalsPassword")
	private String confirmPassword;

	/**
	 * 安全码
	 */
	@Validator(desc = "安全码", nullable = false)
	private String safetyCode;
	
	

	public boolean equalsPassword() {
		return this.confirmPassword != null && this.confirmPassword.equals(this.password);
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

	public String getSafetyCode() {
		return safetyCode;
	}

	public void setSafetyCode(String safetyCode) {
		this.safetyCode = safetyCode;
	}
}
