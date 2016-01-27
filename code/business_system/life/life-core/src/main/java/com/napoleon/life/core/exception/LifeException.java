package com.napoleon.life.core.exception;

public class LifeException extends RuntimeException {

	private static final long serialVersionUID = -5835072341957883405L;
	
	private String errCode;
	private String errChineseMsg;
	
	public LifeException(String errCode, String message, String chineseMsg){
		super(message);
		this.errCode = errCode;
		this.errChineseMsg = chineseMsg;
	}
	
	public LifeException(LifeExceptionEnum exceptionEnum){
		this(exceptionEnum.getCode(), exceptionEnum.getMessage(), exceptionEnum.getChineseMessage());
	}
	
	public LifeException(LifeExceptionEnum exceptionEnum, String errChineseMsg){
		this(exceptionEnum.getCode(), errChineseMsg, errChineseMsg);
	}
	
	public LifeException(String errCode, String errChineseMsg){
		this(errCode, errChineseMsg, errChineseMsg);
	}
	
	public LifeException(Throwable cause){
		super(cause);
		this.errCode = LifeExceptionEnum.SYSTEM_ERR.getCode();
		this.errChineseMsg = LifeExceptionEnum.SYSTEM_ERR.getChineseMessage();
	}
	
	public LifeException(String errChineseMsg, Throwable cause){
		super(errChineseMsg, cause);
		this.errCode = LifeExceptionEnum.SYSTEM_ERR.getCode();
		this.errChineseMsg = errChineseMsg;
	}
	
	
	public LifeException(String message){
		super(message);
		this.errCode = LifeExceptionEnum.SYSTEM_ERR.getCode();
		this.errChineseMsg = message;
	}
	

	public String getErrCode() {
		return errCode;
	}

	public String getErrChineseMsg() {
		return errChineseMsg;
	}
}
