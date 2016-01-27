package com.napoleon.life.core.bean;

import java.util.Date;
import java.util.List;

public class RecieveEmailBean {
	
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 发送时间
	 */
	private Date sentDate;
	
	/**
	 * 是否需要回复
	 */
	private boolean replySign;
	
	/**
	 * 是否已读
	 */
	private boolean isNew;
	
	/**
	 * 是否包含附件
	 */
	private boolean isContainAttach;
	
	/**
	 * 发件人
	 */
	private String from;
	
	/**
	 * 收件人地址
	 */
	private String to;
	
	/**
	 * 抄送人地址
	 */
	private String cc;
	
	/**
	 * 邮件ID
	 */
	private String messageId;
	
	/**
	 * 邮件正文
	 */
	private String bodyText;
	
	/**
	 * 附件路径
	 */
	private List<String> attachPaths;
	
	private List<String> attachNames;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public boolean isReplySign() {
		return replySign;
	}

	public void setReplySign(boolean replySign) {
		this.replySign = replySign;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isContainAttach() {
		return isContainAttach;
	}

	public void setContainAttach(boolean isContainAttach) {
		this.isContainAttach = isContainAttach;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public List<String> getAttachPaths() {
		return attachPaths;
	}

	public void setAttachPaths(List<String> attachPaths) {
		this.attachPaths = attachPaths;
	}

	public List<String> getAttachNames() {
		return attachNames;
	}

	public void setAttachNames(List<String> attachNames) {
		this.attachNames = attachNames;
	}

	@Override
	public String toString() {
		return "RecieveEmailBean [subject=" + subject + ", sentDate="
				+ sentDate + ", replySign=" + replySign + ", isNew=" + isNew
				+ ", isContainAttach=" + isContainAttach + ", from=" + from
				+ ", to=" + to + ", cc=" + cc + ", messageId=" + messageId
				+ ", bodyText=" + bodyText + ", attachPaths=" + attachPaths
				+ ", attachNames=" + attachNames + "]";
	}
}
