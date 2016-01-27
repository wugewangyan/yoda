package com.napoleon.life.core.bean;

import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

public class EmailSendBean {

	private String subject;
	
	private String from;
	
	private String fromEmailPassword;
	
	private InternetAddress[] to;
	
	private String content;
	
	private String templatePath;
	
	private Map<String, String> paramsMap;
	
	private List<String> attachmentPath;
	
	private List<String> contentPicPath;
	
	private List<String> contentIds;
	
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public InternetAddress[] getTo() {
		return to;
	}

	public void setTo(InternetAddress[] to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public List<String> getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(List<String> attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public List<String> getContentPicPath() {
		return contentPicPath;
	}

	public void setContentPicPath(List<String> contentPicPath) {
		this.contentPicPath = contentPicPath;
	}

	public List<String> getContentIds() {
		return contentIds;
	}

	public void setContentIds(List<String> contentIds) {
		this.contentIds = contentIds;
	}

	public String getFromEmailPassword() {
		return fromEmailPassword;
	}

	public void setFromEmailPassword(String fromEmailPassword) {
		this.fromEmailPassword = fromEmailPassword;
	}
}
