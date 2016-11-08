package com.napoleon.life.common.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.napoleon.life.exception.CommonException;

/**
 * 加密工具
 * @author wuge
 *
 */
public class EncryptUtil {
	
	public static final String CHARSET_UTF8 = "UTF-8";

	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	
	public static String encryptWithBase64(String source){
		if(StringUtil.isEmpty(source)){
			return source;
		}
		
		try{
			return Base64.encodeBase64String(source.getBytes(CHARSET_UTF8));
		}catch(Exception e){
			logger.error("Failed to do base 64", e);
		}
		
		return null;
	}
	
	public static String encryptWithBase64(byte[] source){
		try{
			return Base64.encodeBase64String(source);
		}catch(Exception e){
			logger.error("Failed to do base 64", e);
		}
		
		return null;
	}
	
	public static String MD5Encode(String origin, String charsetName){
		String resultStr = null;
		try{
			resultStr = new String(origin);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			if(charsetName == null || "".equals(charsetName)){
				resultStr = byteArrayToHexString(md5.digest(resultStr.getBytes()));
			}else{
				resultStr = byteArrayToHexString(md5.digest(resultStr.getBytes(charsetName)));
			}
		}catch(Exception e){
			logger.error("Failed to do MD5 Encode", e);
		}
		
		return resultStr;
	}
	
	
	public static String getKeyedDigestUTF8(String strSrc, String key) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF8"));

			String result = "";
			byte[] temp = md5.digest(key.getBytes("UTF8"));
			for (int i = 0; i < temp.length; i++) {
				result += Integer.toHexString(
						(0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}

			return result;
		} catch (Exception e) {
			throw new CommonException(e);
		}
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
