package com.napoleon.life.core.service;

import com.napoleon.life.core.dto.LifeUserResetPasswordDto;
import com.napoleon.life.core.entity.LifeUser;
import com.napoleon.life.core.exception.LifeException;

public interface LifeUserService {
	
	/**
	 * 用户注册
	 * @param user
	 * @throws LifeException
	 */
	public void userRegister(LifeUser user) throws LifeException;
	
	/**
	 * 激活账户，如果激活过程中有任何错误，激活失败，如果没有异常，则激活成功
	 * @param encryptUserNo
	 * @param activateCode
	 */
	public void activateAccount(String encryptUserNo, String activateCode);
	
	/**
	 * 重新发送激活邮件
	 * @param encryptUserNo
	 */
	public void resendActivateEmail(String email);
	
	
	/**
	 * 根据email查看用户信息
	 * @param email
	 * @return
	 */
	public LifeUser findByEmail(String email);
	
	
	/**
	 * 验证登录状态
	 * @param email
	 * @param password
	 * @return
	 */
	public LifeUser validLogin(String email, String password);
	
	/**
	 * 发送重置密码邮件
	 * @param email
	 */
	public void sendResetPasswordEmail(String email);

	/**
	 * 重置密码确认操作
	 * @param reset
	 */
	public void confirmPassword(LifeUserResetPasswordDto reset);
	
	
}
