package com.napoleon.life.core.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.napoleon.life.common.util.CryptUtil;
import com.napoleon.life.common.util.EncryptUtil;
import com.napoleon.life.core.bean.ConfigBean;
import com.napoleon.life.core.bean.EmailSendBean;
import com.napoleon.life.core.dao.LifeUserDao;
import com.napoleon.life.core.dto.LifeUserResetPasswordDto;
import com.napoleon.life.core.entity.LifeUser;
import com.napoleon.life.core.enums.ActivateStatusEnum;
import com.napoleon.life.core.enums.UserStatusEnum;
import com.napoleon.life.core.exception.LifeException;
import com.napoleon.life.core.exception.LifeExceptionEnum;
import com.napoleon.life.core.service.LifeEmailService;
import com.napoleon.life.core.service.LifeUserService;
import com.napoleon.life.core.util.Constants;
import com.napoleon.life.core.util.LifeSerialNoService;

@Service
public class LifeUserServiceImpl implements LifeUserService {

	private static final Logger logger = LoggerFactory.getLogger(LifeUserServiceImpl.class);
	
	@Autowired
	private LifeUserDao lifeUserDao;

	@Autowired
	private LifeEmailService lifeEmailService;

	@Autowired
	private LifeSerialNoService lifeSerialNoService;

	@Autowired
	private ConfigBean config;
	
	
	@Override
	public LifeUser findByEmail(String email) {
		return this.lifeUserDao.findByEmail(email);
	}

	@Override
	public void userRegister(LifeUser user) throws LifeException {
		try{
			// 1. 查询该email是否被注册过
			LifeUser lifeUser = this.lifeUserDao.findByEmail(user.getEmail());
			if (lifeUser == null) {
				user.setActivateStatus(ActivateStatusEnum.ACTIVATE_NO.getCode());
				user.setCreateDate(new Timestamp(new Date().getTime()));
				user.setUpdateTime(new Timestamp(new Date().getTime()));
				user.setUserNo(lifeSerialNoService.getSerialNo(Constants.USER_NO));
				user.setStatus(UserStatusEnum.STATUS_FROZEN.getCode());
				String randomCode = EncryptUtil.MD5Encode(UUID.randomUUID().toString(), "UTF8");
				user.setActivateCode(randomCode);
				
				//将密码进行加密处理
				user.setPassword(CryptUtil.encrypt(user.getPassword(), config.getSecureAppKey()));
				user.setPhone(CryptUtil.encrypt(user.getPhone(), config.getSecureAppKey()));
				this.lifeUserDao.add(user);

				EmailSendBean email = new EmailSendBean();
				email.setFrom(config.getMailFrom());
				email.setTo(new InternetAddress[]{new InternetAddress(user.getEmail())});
				email.setSubject("激活您的 LifeManage 账户");
				email.setTemplatePath("email.ftl");
				
				String activateCode = EncryptUtil.encryptWithBase64(user.getActivateCode() + ":" + user.getUserNo());
				String encryptUserNo = CryptUtil.encrypt(user.getUserNo(), config.getSecureAppKey());
				String userName = user.getUserName() == null ? user.getEmail() : user.getUserName();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("activateCode", activateCode);
				map.put("encryptUserNo", encryptUserNo);
				map.put("userName", userName);
				email.setParamsMap(map);
				
				lifeEmailService.sendAccountActivateEmail(email);
				user.setEncryptUserNo(encryptUserNo); // 用户返回给页面
			} else {
				throw new LifeException(LifeExceptionEnum.EMAIL_HAS_REGISTER);
			}
		}catch(LifeException e){
			throw e;
		}catch(Exception e){
			throw new LifeException(e);
		}
		
	}
	
	
	/**
	 * 激活账户，如果激活过程中有任何错误，激活失败，如果没有异常，则激活成功
	 * @param encryptUserNo
	 * @param activateCode
	 */
	public void activateAccount(String encryptUserNo, String activateCode){
		try{
			String userNo = CryptUtil.decrypt(encryptUserNo, config.getSecureAppKey());
			logger.info("The decrypt userNo is : {}", userNo);
			LifeUser lifeUser = this.lifeUserDao.findByUserNo(userNo);
			
			// 如果用户不存在
			if(lifeUser == null){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_NOT_EXIST);
			}
			
			// 如果已经激活
			if(ActivateStatusEnum.ACTIVATE_YES.getCode().equals(lifeUser.getActivateStatus())){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_HAS_ACTIVATE);
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, -config.getActivateExpireMinute());
			
			Calendar updateTime = Calendar.getInstance();
			updateTime.setTime(lifeUser.getUpdateTime());
			
			if(calendar.before(updateTime)){
				// 说明还没有过期，判断激活码是否相等
				String activateCodeLocal = EncryptUtil.encryptWithBase64(lifeUser.getActivateCode() + ":" + userNo);
				if(!activateCodeLocal.equals(activateCode)){
					throw new LifeException(LifeExceptionEnum.ACCOUNT_HAS_ACTIVATE);
				}else{
					lifeUser.setActivateStatus(ActivateStatusEnum.ACTIVATE_YES.getCode());
					lifeUser.setStatus(UserStatusEnum.STATUS_NORMAL.getCode());
					lifeUser.setUpdateTime(new Timestamp(new Date().getTime()));
					this.lifeUserDao.update(lifeUser);
				}
			}else{
				//过期
				throw new LifeException(LifeExceptionEnum.ACTIVATECODE_HAS_EXPIRED);
			}
		}catch(LifeException le){
			throw le;
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
	}
	
	
	public void sendResetPasswordEmail(String email){
		try{
			LifeUser lifeUser = this.findByEmail(email);
			// 如果用户不存在
			if(lifeUser == null){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_NOT_EXIST);
			}
			
			String safetyCode = EncryptUtil.MD5Encode(UUID.randomUUID().toString(), "UTF8");
			lifeUser.setSafetyCode(safetyCode);
			lifeUser.setSafetyCodeTime(new Timestamp(new Date().getTime()));
			this.lifeUserDao.update(lifeUser);
			
			// 再次发送激活邮件
			EmailSendBean emailSendBean = new EmailSendBean();
			emailSendBean.setFrom(config.getMailFrom());
			emailSendBean.setTo(new InternetAddress[]{new InternetAddress(lifeUser.getEmail())});
			emailSendBean.setSubject("重置您的 LifeManage 账户密码");
			emailSendBean.setTemplatePath("reset-password-email.ftl");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("safetyCode", safetyCode);
			map.put("userName", lifeUser.getUserName() == null ? lifeUser.getEmail() : lifeUser.getUserName());
			emailSendBean.setParamsMap(map);
			
			lifeEmailService.sendAccountActivateEmail(emailSendBean);
		}catch(LifeException le){
			throw le;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
	}
	
	public void resendActivateEmail(String email){
		try{
			LifeUser lifeUser = this.lifeUserDao.findByEmail(email);
			
			// 如果用户不存在
			if(lifeUser == null){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_NOT_EXIST);
			}
			
			// 如果已经激活
			if(ActivateStatusEnum.ACTIVATE_YES.getCode().equals(lifeUser.getActivateStatus())){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_HAS_ACTIVATE);
			}
			
			String activateCode = EncryptUtil.MD5Encode(UUID.randomUUID().toString(), "UTF8");
			
			// 先更新激活码和更新时间，以便再次校验激活码
			lifeUser.setActivateStatus(ActivateStatusEnum.ACTIVATE_NO.getCode());
			lifeUser.setUpdateTime(new Timestamp(new Date().getTime()));
			lifeUser.setActivateCode(activateCode);
			this.lifeUserDao.update(lifeUser);
			
			// 再次发送激活邮件
			EmailSendBean emailBean = new EmailSendBean();
			emailBean.setFrom(config.getMailFrom());
			emailBean.setTo(new InternetAddress[]{new InternetAddress(email)});
			emailBean.setSubject("重新激活您的 LifeManage 账户");
			emailBean.setTemplatePath("email.ftl");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("activateCode", EncryptUtil.encryptWithBase64(activateCode + ":" + lifeUser.getUserNo()));
			map.put("encryptUserNo", CryptUtil.encrypt(lifeUser.getUserNo(), config.getSecureAppKey()));
			map.put("userName", lifeUser.getUserName() == null ? lifeUser.getEmail() : lifeUser.getUserName());
			emailBean.setParamsMap(map);
			
			lifeEmailService.sendAccountActivateEmail(emailBean);
		}catch(LifeException le){
			throw le;
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
	}
	
	public LifeUser validLogin(String email, String password){
		LifeUser lifeUser = null;
		try{
			lifeUser = this.findByEmail(email);
			
			// 如果用户不存在
			if(lifeUser == null){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_NOT_EXIST);
			}
			
			String cryptPassword = CryptUtil.encrypt(password, config.getSecureAppKey());
			// 如果密码不正确
			if(!cryptPassword.equals(lifeUser.getPassword())){
				throw new LifeException(LifeExceptionEnum.PASSWORD_WRONG);
			}
			
			// 如果账号还没有被激活
			if(ActivateStatusEnum.ACTIVATE_NO.getCode().equals(lifeUser.getActivateStatus())){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_NOT_ACTIVATE);
			}
			
			// 如果用户状态不可用［冻结，注销］
			if(!UserStatusEnum.STATUS_NORMAL.getCode().equals(lifeUser.getStatus())){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_STATUS_INVALID);
			}
			return lifeUser;
		}catch(LifeException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
	}
	
	@Override
	public void confirmPassword(LifeUserResetPasswordDto reset) {
		try{
			LifeUser lifeUser = this.findByEmail(reset.getEmail());
			
			// 如果用户不存在
			if(lifeUser == null){
				throw new LifeException(LifeExceptionEnum.ACCOUNT_NOT_EXIST);
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, -config.getActivateExpireMinute());
			
			Calendar safetyCodeTime = Calendar.getInstance();
			safetyCodeTime.setTime(lifeUser.getSafetyCodeTime());
			
			if(calendar.before(safetyCodeTime)){
				// 说明还没有过期，判断激活码是否相等
				if(!lifeUser.getSafetyCode().equals(reset.getSafetyCode())){
					throw new LifeException(LifeExceptionEnum.SAFETY_CODE_FAIL);
				}else{
					lifeUser.setPassword(CryptUtil.encrypt(reset.getPassword(), config.getSecureAppKey()));
					lifeUser.setUpdateTime(new Timestamp(new Date().getTime()));
					this.lifeUserDao.update(lifeUser);
					
					// 发送通知邮件
					// 再次发送激活邮件
					EmailSendBean email = new EmailSendBean();
					email.setFrom(config.getMailFrom());
					email.setTo(new InternetAddress[]{new InternetAddress(lifeUser.getEmail())});
					email.setSubject("LifeManage 账户密码变更通知");
					email.setTemplatePath("password-reset-notify.ftl");
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("userName", lifeUser.getUserName() == null ? lifeUser.getEmail() : lifeUser.getUserName());
					map.put("email", reset.getEmail());
					email.setParamsMap(map);
					
					lifeEmailService.sendAccountActivateEmail(email);
				}
			}else{
				//过期
				throw new LifeException(LifeExceptionEnum.RESET_EMAIL_HAS_EXPIRED);
			}
		}catch(LifeException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
	}
}
