package com.napoleon.life.common.util.validator;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import com.napoleon.life.common.util.RSAUtils;

public class RSATest {

	@Test
	public void test(){
		RSAPrivateKey privateKey = RSAUtils.getDefaultPrivateKey();
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		
		System.out.println("privateExponent--->" + privateKey.getPrivateExponent().toString(16));
		System.out.println("privateModulus---> " + privateKey.getModulus().toString(16));
		
		String publicExponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
		String publicModulus = new String(Hex.encodeHexString(publicKey.getModulus().toByteArray()));
		System.out.println("publicExponent---> " + publicExponent);
		System.out.println("publicModulus--->  " + publicModulus);
		
		RSAPublicKey clientPublicKey = RSAUtils.getRSAPublidKey(publicModulus, publicExponent);
		String encrypedPassword = RSAUtils.encryptString(clientPublicKey, "Wuge@19900619");
		System.out.println("encrypedPassword---> " + encrypedPassword);
		
		String descrypedPassword = RSAUtils.decryptString(privateKey, encrypedPassword);
		System.out.println("descrypedPassword--->" + descrypedPassword);
		
		String decryptJs = RSAUtils.decryptStringByJs(encrypedPassword);
		System.out.println("decryptJs--->" + decryptJs);
	}
}
