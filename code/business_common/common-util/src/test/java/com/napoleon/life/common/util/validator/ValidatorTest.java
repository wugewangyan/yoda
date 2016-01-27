package com.napoleon.life.common.util.validator;

import java.util.UUID;

import org.junit.Test;

import com.napoleon.life.common.util.EncryptUtil;

public class ValidatorTest {
	
	
	
	@Test
	public void MD5(){
		String randomCode = UUID.randomUUID().toString();
		System.out.println("--> randomCode : " + randomCode);
		String md5Code = EncryptUtil.MD5Encode(randomCode, "UTF8");
		System.out.println("--> md5Code : " + md5Code);
		String base64 = EncryptUtil.encryptWithBase64(md5Code);
		System.out.println("--> base64 : " + base64);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
