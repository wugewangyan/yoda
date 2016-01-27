package com.napoleon.life.core.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigBean {

	
	@Value("${mail.transport.protocol}")
	private String mailProtocol;		
	
	@Value("${mail.smtp.host}")  
	private String mailHost;
			
	@Value("${mail.smtp.port}")
	private String mailProt;
					
	@Value("${mail.smtp.auth}")
	private String mailAuth;
	
	@Value("${mail.smtp.username}")
	private String mailUserName;
	
	@Value("${mail.smtp.password}")
	private String mailPassword;
	
	@Value("${mail.from}")
	private String mailFrom;
	
	@Value("${secure.app.key}")
	private String secureAppKey;
	
	@Value("${activate.expire.minute}")
	private Integer activateExpireMinute;

	public String getMailProtocol() {
		return mailProtocol;
	}

	public void setMailProtocol(String mailProtocol) {
		this.mailProtocol = mailProtocol;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailProt() {
		return mailProt;
	}

	public void setMailProt(String mailProt) {
		this.mailProt = mailProt;
	}

	public String getMailAuth() {
		return mailAuth;
	}

	public void setMailAuth(String mailAuth) {
		this.mailAuth = mailAuth;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getSecureAppKey() {
		return secureAppKey;
	}

	public void setSecureAppKey(String secureAppKey) {
		this.secureAppKey = secureAppKey;
	}

	public Integer getActivateExpireMinute() {
		return activateExpireMinute;
	}

	public void setActivateExpireMinute(Integer activateExpireMinute) {
		this.activateExpireMinute = activateExpireMinute;
	}
	
}
