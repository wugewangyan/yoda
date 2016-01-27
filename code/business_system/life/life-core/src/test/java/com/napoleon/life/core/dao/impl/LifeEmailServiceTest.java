package com.napoleon.life.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.napoleon.life.core.bean.EmailSendBean;
import com.napoleon.life.core.bean.RecieveEmailBean;
import com.napoleon.life.core.service.LifeEmailService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class LifeEmailServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(LifeEmailServiceTest.class);

	@Autowired
	private LifeEmailService lifeEmailService;
	
	@Test
	public void sendEmail(){
		
		EmailSendBean emailSendBean = new EmailSendBean();
		emailSendBean.setFrom("243399199@qq.com");
		emailSendBean.setFromEmailPassword("oynffljntamxcbcj");
		try{
			emailSendBean.setTo(new InternetAddress[]{new InternetAddress("2732650059@qq.com")});
		}catch(Exception e){}
		
		emailSendBean.setTemplatePath("email.ftl");
		Map<String, String> map = new HashMap<String, String>();
		map.put("activateCode", "activateCode");
		map.put("encryptUserNo", "encryptUserNo");
		map.put("userName", "吴革");
		emailSendBean.setParamsMap(map);
		List<String> attachmentPath = new ArrayList<String>();
		attachmentPath.add("/Users/wuge/Downloads/login.js");
		emailSendBean.setAttachmentPath(attachmentPath);
		
		
		List<String> contentIds = new ArrayList<String>();
		contentIds.add("logo_jpg");
		emailSendBean.setContentIds(contentIds);
		
		List<String> contentPicPath = new ArrayList<String>();
		contentPicPath.add("/Users/wuge/Downloads/logo_jpg.png");
		emailSendBean.setContentPicPath(contentPicPath);
		
		this.lifeEmailService.sendEmail(emailSendBean);
	}
	
	
	@Test
	public void recieveEmail(){
		List<RecieveEmailBean> result = this.lifeEmailService.recieveEmail();
		Assert.notNull(result);
		for(RecieveEmailBean email : result){
			logger.info(email.toString());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
