package com.napoleon.life.framework.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.napoleon.life.exception.CommonException;
import com.napoleon.life.exception.CommonResultCode;
import com.napoleon.life.framework.result.CommonRltUtil;

public class BaseController {

	@ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String rlt = null;
        if (e instanceof CommonException) {
        	CommonException be = (CommonException) e;
        	rlt = CommonRltUtil.createCommonRltToString(be.getErrCode(), be.getErrChineseMsg());
        } else {
        	rlt = CommonRltUtil.createCommonRltToString(CommonResultCode.SYSTEM_ERR);
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<String>(rlt, headers, HttpStatus.OK);
    }
}
