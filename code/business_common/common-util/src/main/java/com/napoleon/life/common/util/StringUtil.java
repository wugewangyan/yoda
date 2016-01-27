package com.napoleon.life.common.util;

public class StringUtil {

	/**
     * 判断字符串是否不为NULL和空串和"null"。
     * 
     * @param String
     * @return boolean
     */
    public static boolean notEmpty(String str) {
        return str != null && str.trim().length() > 0 && !str.toLowerCase().equals("null");

    }
    
    /**
     * 判断字符串为NULL和空串和"null"
     * 
     * @param String
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return !notEmpty(str);
    }
    
    /**
     * 判断输入的object为空
     * 
     * @param Object 输入字符串
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        return (object == null) || object.toString().trim().length() == 0;
    }
}

