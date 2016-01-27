package com.napoleon.life.core.util;

import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.napoleon.life.core.bean.EmailSendBean;

public class MailUtil {

	/**  
     * 根据传入的文件路径创建附件并返回  
     */ 
    public static void createAttachment(List<String> fileNames, MimeMultipart allPart) throws Exception { 
    	if(fileNames != null && fileNames.size() > 0){
    		for(String fileName : fileNames){
    			MimeBodyPart attachmentPart = new MimeBodyPart();  
    	        FileDataSource fds = new FileDataSource(fileName);  
    	        attachmentPart.setDataHandler(new DataHandler(fds));  
    	        attachmentPart.setFileName(fds.getName()); 
    	        
    	        allPart.addBodyPart(attachmentPart);
    		}
    	}
    }  
	
    /**  
     * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分  
     */ 
    public static MimeBodyPart createContent(String body, List<String> contentPicPath, List<String> contentIds)  
            throws Exception {  
    	// 用于保存最终正文部分  
        MimeBodyPart contentBody = new MimeBodyPart();  
    	
    	// 用于组合文本和图片，"related"型的MimeMultipart对象  
        MimeMultipart contentMulti = new MimeMultipart("related");
        
        // 正文的文本部分  
        MimeBodyPart textBody = new MimeBodyPart();  
        textBody.setContent(body, "text/html;charset=gbk");  
        contentMulti.addBodyPart(textBody);  
 
        if(contentPicPath != null && contentPicPath.size() > 0){
        	for(int i = 0; i < contentPicPath.size(); i++){
        		String picPath = contentPicPath.get(i);
        		String contentId = contentIds.get(i);
        		// 正文的图片部分  
                MimeBodyPart jpgBody = new MimeBodyPart();  
                FileDataSource fds = new FileDataSource(picPath);  
                jpgBody.setDataHandler(new DataHandler(fds));  
                jpgBody.setContentID(contentId); 
                
                contentMulti.addBodyPart(jpgBody); 
        	}
        }
 
        // 将上面"related"型的 MimeMultipart 对象作为邮件的正文  
        contentBody.setContent(contentMulti);  
        return contentBody;  
    }  
    
    
    /**  
     * 根据传入的 Seesion 对象创建混合型的 MIME消息  
     */ 
    public static MimeMessage createMessage(Session session, EmailSendBean emailSendBean) throws Exception {  
        MimeMessage msg = new MimeMessage(session);  
        msg.setFrom(new InternetAddress(emailSendBean.getFrom()));  
        msg.setRecipients(RecipientType.TO, emailSendBean.getTo());
        msg.setSubject(emailSendBean.getSubject()); 
        
        // 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象  
        MimeMultipart allPart = new MimeMultipart("mixed");  
 
        // 创建邮件的各个 MimeBodyPart 部分  
        // 1. 创建附件
        createAttachment(emailSendBean.getAttachmentPath(), allPart);
        // 2. 创建图文并茂的邮件内容
        MimeBodyPart content = createContent(emailSendBean.getContent(), emailSendBean.getContentPicPath(), emailSendBean.getContentIds());  
        allPart.addBodyPart(content);  
 
        // 将上面混合型的 MimeMultipart 对象作为邮件内容并保存  
        msg.setContent(allPart); 
        return msg;  
    }  
}
