package com.napoleon.life.common.util.exception;

public class LifeUtilException extends RuntimeException {

	private static final long serialVersionUID = -5835072341957883405L;
	
	private String errCode;
	private String errChineseMsg;
	
	public LifeUtilException(String errCode, String message, String chineseMsg){
		super(message);
		this.errCode = errCode;
		this.errChineseMsg = chineseMsg;
	}
	
	public LifeUtilException(UtilExceptionEnum exceptionEnum){
		this(exceptionEnum.getCode(), exceptionEnum.getMessage(), exceptionEnum.getChineseMessage());
	}
	
	public LifeUtilException(UtilExceptionEnum exceptionEnum, String errChineseMsg){
		this(exceptionEnum.getCode(), errChineseMsg, errChineseMsg);
	}
	
	public LifeUtilException(String errCode, String errChineseMsg){
		this(errCode, errChineseMsg, errChineseMsg);
	}
	
	public LifeUtilException(Throwable cause){
		super(cause);
		this.errCode = UtilExceptionEnum.COMMON_UTIL_ERR.getCode();
		this.errChineseMsg = UtilExceptionEnum.COMMON_UTIL_ERR.getChineseMessage();
	}
	
	public LifeUtilException(String errChineseMsg, Throwable cause){
		super(errChineseMsg, cause);
		this.errCode = UtilExceptionEnum.COMMON_UTIL_ERR.getCode();
		this.errChineseMsg = errChineseMsg;
	}
	
	
	public LifeUtilException(String message){
		super(message);
		this.errCode = UtilExceptionEnum.COMMON_UTIL_ERR.getCode();
		this.errChineseMsg = message;
	}
	

	public String getErrCode() {
		return errCode;
	}

	public String getErrChineseMsg() {
		return errChineseMsg;
	}
}
