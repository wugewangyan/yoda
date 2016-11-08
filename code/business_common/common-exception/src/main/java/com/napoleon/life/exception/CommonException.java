package com.napoleon.life.exception;

public class CommonException extends RuntimeException {

	private static final long serialVersionUID = -5835072341957883405L;
	
	private String errCode;
	private String errChineseMsg;
	
	public CommonException(String errCode, String message, String chineseMsg){
		super(message);
		this.errCode = errCode;
		this.errChineseMsg = chineseMsg;
	}
	
	public CommonException(CommonResultCode exceptionEnum){
		this(exceptionEnum.getCode(), exceptionEnum.getMessage(), exceptionEnum.getChineseMessage());
	}
	
	public CommonException(CommonResultCode exceptionEnum, String errChineseMsg){
		this(exceptionEnum.getCode(), errChineseMsg, errChineseMsg);
	}
	
	public CommonException(String errCode, String errChineseMsg){
		this(errCode, errChineseMsg, errChineseMsg);
	}
	
	public CommonException(Throwable cause){
		super(cause);
		this.errCode = CommonResultCode.SYSTEM_ERR.getCode();
		this.errChineseMsg = CommonResultCode.SYSTEM_ERR.getChineseMessage();
	}
	
	public CommonException(String errChineseMsg, Throwable cause){
		super(errChineseMsg, cause);
		this.errCode = CommonResultCode.SYSTEM_ERR.getCode();
		this.errChineseMsg = errChineseMsg;
	}
	
	
	public CommonException(String message){
		super(message);
		this.errCode = CommonResultCode.SYSTEM_ERR.getCode();
		this.errChineseMsg = message;
	}
	

	public String getErrCode() {
		return errCode;
	}

	public String getErrChineseMsg() {
		return errChineseMsg;
	}
}
