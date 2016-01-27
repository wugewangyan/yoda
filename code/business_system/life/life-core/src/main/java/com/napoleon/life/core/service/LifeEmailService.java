package com.napoleon.life.core.service;

import java.util.List;

import com.napoleon.life.core.bean.EmailSendBean;
import com.napoleon.life.core.bean.RecieveEmailBean;

public interface LifeEmailService {

	/**
	 * 为注册激活专门设置的发送email的方法
	 * @param email
	 */
	public void sendAccountActivateEmail(EmailSendBean email);
	
	/**
	 * 发送普通的email
	 * @param emailSendBean
	 */
	public void sendEmail(EmailSendBean emailSendBean);
	
	
	/**
	 * 接受邮件
	 * @return
	 */
	public List<RecieveEmailBean> recieveEmail();
	
}
