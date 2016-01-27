package com.napoleon.life.core.dto;

import com.napoleon.life.common.util.validator.Validator;

public class LifeUserLoginDto {

	/**
	 * 邮箱账号
	 */
	@Validator(desc = "邮箱账号", isEmail = true, nullable = false)
	private String e;

	/**
	 * 密码
	 */
	@Validator(desc = "密码", minLength = 8, maxLength = 20, nullable = false)
	private String p;
	
	private String rememberMe;

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}
}
