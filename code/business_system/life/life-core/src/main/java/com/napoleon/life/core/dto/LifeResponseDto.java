package com.napoleon.life.core.dto;

import com.napoleon.life.common.util.exception.LifeUtilException;
import com.napoleon.life.core.enums.StatusEnum;
import com.napoleon.life.core.exception.LifeException;
import com.napoleon.life.core.exception.LifeExceptionEnum;

public class LifeResponseDto {

	
	/** 状态【0-已受理；1-成功；2-失败】 */
	private Integer status;
	
	private String errorCode;
	
	private String errorMsg;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public void buildLifeException(LifeException e){
		this.status = StatusEnum.STATUS_FAIL.getCode();
		this.errorCode = e.getErrCode();
		this.errorMsg = e.getErrChineseMsg();
	}
	
	public void buildLifeUtilException(LifeUtilException e){
		this.status = StatusEnum.STATUS_FAIL.getCode();
		this.errorCode = e.getErrCode();
		this.errorMsg = e.getErrChineseMsg();
	}
	
	
	public void buildException(Exception e){
		this.status = StatusEnum.STATUS_FAIL.getCode();
		this.errorCode = LifeExceptionEnum.SYSTEM_ERR.getCode();
		this.errorMsg = e.getMessage();
	}
}
