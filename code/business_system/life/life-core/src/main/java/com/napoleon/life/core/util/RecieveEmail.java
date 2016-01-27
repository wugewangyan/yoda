package com.napoleon.life.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.napoleon.life.core.bean.RecieveEmailBean;
import com.napoleon.life.core.exception.LifeException;
import com.sun.mail.imap.IMAPMessage;

public class RecieveEmail {
	
	private static Logger logger = LoggerFactory.getLogger(RecieveEmail.class);
	
	private StringBuffer bodyText = new StringBuffer();
	private List<String> attachNames = new ArrayList<String>();
	private List<String> attachPaths = new ArrayList<String>();
	private String saveAttachPath;
	private IMAPMessage mimeMessage;
	
	public RecieveEmail(IMAPMessage mimeMessage, String saveAttachPath){
		this.mimeMessage = mimeMessage;
		this.saveAttachPath = saveAttachPath;
	}
	
	public RecieveEmailBean parseEmail(){
		RecieveEmailBean result = new RecieveEmailBean();
		try{
			result.setSubject(getSubject());
	        result.setSentDate(getSentDate());
	        result.setReplySign(getReplySign());
	        result.setNew(isNew());
	        result.setContainAttach(isContainAttach(this.mimeMessage));
	        if(result.isContainAttach()){
	        	this.saveAttachMent(this.mimeMessage);
	        	result.setAttachNames(this.attachNames);
	        	result.setAttachPaths(this.attachPaths);
	        }
	        result.setFrom(getFrom());
	        result.setTo(getMailAddress("to"));
	        result.setCc(getMailAddress("cc"));
	        result.setSentDate(getSentDate());
	        result.setMessageId(getMessageId());
	        
	        getMailContent((Part) this.mimeMessage);
	        result.setBodyText(this.bodyText.toString());
	        
	        return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new LifeException(e);
		}
		
	}
	
	
	public void getMailContent(Part part) throws Exception {
   	 
        String contentType = part.getContentType();
        // 获得邮件的MimeType类型
        System.out.println("邮件的MimeType类型: " + contentType);
 
        int nameIndex = contentType.indexOf("name");
 
        boolean conName = false;
 
        if (nameIndex != -1) {
            conName = true;
        }
 
        System.out.println("邮件内容的类型:　" + contentType);
 
        if (part.isMimeType("text/plain") && conName == false) {
            // text/plain 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && conName == false) {
            // text/html 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            // multipart/*
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            // message/rfc822
            getMailContent((Part) part.getContent());
        }
    }
	
	
	/**
     * 　*　保存附件 　
     */
 
    public void saveAttachMent(Part part) throws Exception {
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                String fileName = mPart.getFileName();
                if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                    fileName = MimeUtility.decodeText(fileName);
                }
                this.attachNames.add(fileName);
                
//                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
//                    saveFile(fileName, mPart.getInputStream());
//                } else if (mPart.isMimeType("multipart/*")) {
//                    saveAttachMent(mPart);
//                } else {
//                	saveFile(fileName, mPart.getInputStream());
//                }
                
                if (mPart.isMimeType("multipart/*")) {
                  saveAttachMent(mPart);
                } else {
                	this.attachPaths.add(saveFile(fileName, mPart.getInputStream()));
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
    }
    
    
    /**
     * 　*　真正的保存附件到指定目录里 　
     */
    private String saveFile(String fileName, InputStream in) throws Exception {
        String osName = System.getProperty("os.name");
        String storeDir = this.saveAttachPath;
        String separator = "";
        if (osName == null) {
            osName = "";
        }
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storeDir == null || storeDir.equals(""))
                storeDir = "c:\\tmp";
        } else {
            separator = "/";
            if (storeDir == null || storeDir.equals(""))
            	storeDir = "/tmp";
        }
        File storeFile = new File(storeDir + separator + fileName);
        System.out.println("附件的保存地址:　" + storeFile.toString());
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
 
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
        
        return storeFile.getParent();
    }
    
    
    /**
     * 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachFlag = false;
        // String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                                .equals(Part.INLINE))))
                    attachFlag = true;
                else if (mPart.isMimeType("multipart/*")) {
                    attachFlag = isContainAttach((Part) mPart);
                } else {
                    String conType = mPart.getContentType();
 
                    if (conType.toLowerCase().indexOf("application") != -1)
                        attachFlag = true;
                    if (conType.toLowerCase().indexOf("name") != -1)
                        attachFlag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }
    
    
    /**
     * 　　*　判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false" 　
     */
    public boolean getReplySign() throws MessagingException {
 
        boolean replySign = false;
 
        String needReply[] = mimeMessage
                .getHeader("Disposition-Notification-To");
 
        if (needReply != null) {
            replySign = true;
        }
        return replySign;
    }
 
    /**
     *　获得此邮件的Message-ID 　　
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }
 
    /**
     * 判断此邮件是否已读，如果未读返回false,反之返回true
     */
    public boolean isNew() throws MessagingException {
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * 　*　获得邮件主题 　
     */
    public String getSubject() throws MessagingException {
        String subject = "";
        try {
            logger.info("转换前的subject: {}", mimeMessage.getSubject());
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            logger.info("转换后的subject: {}", mimeMessage.getSubject());
            return subject;
        } catch (Exception exce) {
            throw new LifeException(exce);
        }
    }
 
    /**
     * 　*　获得邮件发送日期 　
     */
    public Date getSentDate() throws Exception {
    	return mimeMessage.getSentDate();
    }
    
    /**
     * 　*　获得发件人的地址和姓名 　
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            logger.warn("无法获取到发件人");
        }
        String personal = address[0].getPersonal();
 
        if (personal == null) {
            logger.warn("无法知道发送者的姓名.");
        }
 
        String fromAddr = null;
        if (personal != null || from != null) {
            fromAddr = personal + "<" + from + ">";
            logger.info("发送者是：{}", fromAddr);
        }
        return fromAddr;
    }
 
    /**
     * 　*　获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     * 　*　"to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址 　
     */
    public String getMailAddress(String type) throws Exception {
        String mailAddr = "";
        String addType = type.toUpperCase();
 
        InternetAddress[] address = null;
        if (addType.equals("TO") || addType.equals("CC")
                || addType.equals("BCC")) {
 
            if (addType.equals("TO")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.TO);
            } else if (addType.equals("CC")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.BCC);
            }
 
            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String emailAddr = address[i].getAddress();
                    if (emailAddr == null) {
                        emailAddr = "";
                    } else {
                        System.out.println("转换之前的emailAddr: " + emailAddr);
                        emailAddr = MimeUtility.decodeText(emailAddr);
                        System.out.println("转换之后的emailAddr: " + emailAddr);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null) {
                        personal = "";
                    } else {
                        System.out.println("转换之前的personal: " + personal);
                        personal = MimeUtility.decodeText(personal);
                        System.out.println("转换之后的personal: " + personal);
                    }
                    String compositeto = personal + "<" + emailAddr + ">";
                    System.out.println("完整的邮件地址：" + compositeto);
                    mailAddr += "," + compositeto;
                }
                mailAddr = mailAddr.substring(1);
            }
        } else {
            throw new Exception("错误的电子邮件类型!");
        }
        return mailAddr;
    }
}
