package com.napoleon.life.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.napoleon.life.core.bean.ConfigBean;
import com.napoleon.life.core.bean.EmailSendBean;
import com.napoleon.life.core.bean.RecieveEmailBean;
import com.napoleon.life.core.exception.LifeException;
import com.napoleon.life.core.service.LifeEmailService;
import com.napoleon.life.core.util.MailUtil;
import com.napoleon.life.core.util.RecieveEmail;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.util.MailSSLSocketFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class LifeEmailServiceImpl implements LifeEmailService {

	private static final Logger logger = LoggerFactory.getLogger(LifeEmailServiceImpl.class);
	
	@Autowired
	private ConfigBean config;
	
	@Override
	public void sendAccountActivateEmail(EmailSendBean email) {
		Session session = createSession();
		MimeMessage message = new MimeMessage(session);
		Transport transport = null;
		try{
			message.setSubject(email.getSubject());
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipients(RecipientType.TO, email.getTo());
			message.setContent(this.getContent(email.getParamsMap(), email.getTemplatePath()), "text/html;charset=utf8");
			
			transport = session.getTransport();
			transport.connect(config.getMailUserName(), config.getMailPassword());// 发件人邮箱号243399199// 和密码oynffljntamxcbcj
			transport.sendMessage(message, message.getRecipients(RecipientType.TO));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}finally{
			try {
				if(transport != null){
					transport.close();
				}
			} catch (MessagingException e) {
				throw new LifeException(e);
			}
		}
	}
	
	
	@Override
	public void sendEmail(EmailSendBean emailSendBean) {
		Session session = createSession();
		Transport transport = null;
		try{
			// 先生产body的内容
			emailSendBean.setContent(this.getContent(emailSendBean.getParamsMap(), emailSendBean.getTemplatePath()));
			MimeMessage message = MailUtil.createMessage(session, emailSendBean);
			
			message.setSubject(emailSendBean.getSubject());
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(emailSendBean.getFrom()));
			message.setRecipients(RecipientType.TO, emailSendBean.getTo());
			
			transport = session.getTransport();
			transport.connect(emailSendBean.getFrom(), emailSendBean.getFromEmailPassword());// 发件人邮箱号243399199// 和密码oynffljntamxcbcj
			transport.sendMessage(message, message.getRecipients(RecipientType.TO));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}finally{
			try {
				transport.close();
			} catch (MessagingException e) {
				throw new LifeException(e);
			}
		}
	}
	
	
	public List<RecieveEmailBean> recieveEmail(){
	    // 使用Properties对象获得Session对象  
	    Session mailSession = this.createRecieveSession();  
	 
	    Folder folder = null;  
	    try{          
	        // 利用Session对象获得Store对象，并连接pop3服务器  
	        Store store = mailSession.getStore("imap");  
	        store.connect("imap.qq.com", "243399199@qq.com", config.getMailPassword());  
	          
	        // 获得邮箱内的邮件夹Folder对象，以"读-写"打开  
	        folder = store.getFolder("INBOX");  
	        folder.open(Folder.READ_WRITE);  
	        
	        Message [] messages = folder.getMessages(); 
	        if(messages != null && messages.length > 0){
	        	List<RecieveEmailBean> result = new ArrayList<RecieveEmailBean>();
	        	for(int i = 0; i < 10; i++){
		        	RecieveEmailBean emailBean = new RecieveEmail((IMAPMessage)messages[i], "/Users/wuge/Downloads").parseEmail();
		        	result.add(emailBean);
		        }
	        	
	        	return result;
	        }else{
	        	return null;
	        }
	        
	    }catch(Exception e){  
	        logger.error(e.getMessage(), e);
	        throw new LifeException(e);
	    } 
	}
	
	
	
	private Session createSession(){
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", config.getMailProtocol());  // smtp
		props.setProperty("mail.smtp.auth", config.getMailAuth()); //true 
		
        props.setProperty("mail.smtp.host", config.getMailHost());  //smtp.qq.com
        props.setProperty("mail.smtp.port", config.getMailProt());  //465
        
		try {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");  
	        props.put("mail.smtp.ssl.socketFactory", sf); 
	        
	        Session session = Session.getDefaultInstance(props);
	        session.setDebug(true);
	        return session;
		} catch (GeneralSecurityException e) {
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}  
	}
	
	private Session createRecieveSession(){
		Properties props = new Properties();
		props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
	    props.setProperty("mail.imap.socketFactory.port", "993");  
	    props.setProperty("mail.store.protocol","imap");    
	    props.setProperty("mail.imap.host", "imap.qq.com");    
	    props.setProperty("mail.imap.port", "993");    
	    props.setProperty("mail.imap.auth.login.disable", "true"); 
		try {
	        Session session = Session.getDefaultInstance(props);
	        session.setDebug(true);
	        return session;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}  
	}
	
	
	private String getContent(Map<String, String> root, String templatePath) {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "");
		
		try {
			Template t = cfg.getTemplate(templatePath, "UTF-8");
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			t.process(root, new OutputStreamWriter(byteOutput));
			return byteOutput.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
	}
}
