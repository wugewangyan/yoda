package com.napoleon.life.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.napoleon.life.exception.CommonException;

public class SignUtil {

	private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

	public static boolean checkSign(String source, String targetSignature, String signKey){
		try{
		if(source.isEmpty() || StringUtil.isEmpty(targetSignature)){
			return false;
		}
		
		String signedStr = EncryptUtil.getKeyedDigestUTF8(source, signKey);
		logger.debug("target sign : {}", signedStr);
		return signedStr != null && signedStr.equals(targetSignature);
		}catch(Exception e){
			logger.error(String.format("Failed to sign %s, with key: %s", EncryptUtil.encryptWithBase64(source), signKey), e);
			throw new CommonException(e);
		}
	}
}
