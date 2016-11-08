package com.napoleon.life.common.util;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.napoleon.life.exception.CommonException;


public class CryptUtil {
    private static final Logger logger = LoggerFactory.getLogger(CryptUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String CIHPER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_IV = "0102030405060708";

    public static String encrypt(String source, String key) {
        try {
            if (StringUtil.isEmpty(source)) {
                return source;
            }

            if (StringUtil.isEmpty(key) || 16 != key.length()) {
                throw new IllegalArgumentException("Invalid key.");
            }

            byte[] raw = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIHPER_TRANSFORMATION);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(DEFAULT_IV.getBytes(DEFAULT_CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(source.getBytes(DEFAULT_CHARSET));

            return byte2hex(encrypted);
        }
        catch (Exception e) {
            logger.error("Fail to encrypt, due to " + e.getMessage());
            throw new CommonException(e);
        }
    }

    public static String decrypt(String source, String key) {
        try {
            if (StringUtil.isEmpty(source)) {
                return source;
            }
            if (StringUtil.isEmpty(key) || 16 != key.length()) {
                throw new IllegalArgumentException("Invalid key.");
            }

            byte[] raw = key.getBytes(DEFAULT_CHARSET);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIHPER_TRANSFORMATION);
            IvParameterSpec ivParameter = new IvParameterSpec(DEFAULT_IV.getBytes(DEFAULT_CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameter);
            byte[] encrypted = hex2byte(source.getBytes(DEFAULT_CHARSET));
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, DEFAULT_CHARSET);
        }
        catch (Exception e) {
            logger.error("Fail to decrypt, due to " + e.getMessage());
            throw new CommonException(e);
        }
    }
    
 // Secure Utilities
    public static String byte2hex(byte[] bytes) {
        String hex = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xFF);
            if (stmp.length() == 1) {
                hex += ("0" + stmp);
            }
            else {
                hex += stmp;
            }
        }
        return hex.toUpperCase();
    }
    
    public static byte[] hex2byte(byte[] bytes) {
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("len of bytes is not a even number.");
        }
        byte[] b2 = new byte[bytes.length / 2];

        for (int n = 0; n < bytes.length; n += 2) {
            String item = new String(bytes, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}

