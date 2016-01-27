package com.napoleon.life.common.util.validator;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ValidatorUtil {
	private static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);
	
	private static Map<String, Pattern> expressionPatternMap = new HashMap<String, Pattern>();
    private static ReentrantLock patternLock = new ReentrantLock();
	
	private static final String ENUM_SCOPE_METHOD = "toEnum";
	private static final String AMT_CONSTRUCTURE = "^0|(([1-9][0-9]{0,9}|0)(\\.[0-9]{2}))$";
	private static final String EMAIL_PATTERN = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	private static final String IDENTITY_PATTERN = "^[A-Za-z0-9]+$";
	private static final String MOBILE_PATTERN = "^(1[0-9])\\d{9}$";
	
	public static boolean checkdependesOn(String dependMethod, Class<?> clazz, Object validatedObj){
		try{
			Method dependencyMethod = clazz.getMethod(dependMethod);
			return (Boolean)dependencyMethod.invoke(validatedObj);
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean checkenumScope(Class<?> scopeEnum, Object value){
		if(value == null){
			return false;
		}
		
		try{
			if(Enum.class.isAssignableFrom(scopeEnum)){
				Method toEnumMethod = null;
				Object result = null;
				try{
					toEnumMethod = scopeEnum.getDeclaredMethod(ENUM_SCOPE_METHOD, String.class);
					result = toEnumMethod.invoke(null, String.valueOf(value));
				}catch(NoSuchMethodException e){
					toEnumMethod = scopeEnum.getDeclaredMethod(ENUM_SCOPE_METHOD, Integer.class);
					result = toEnumMethod.invoke(null, Integer.parseInt(String.valueOf(value)));
				}
				
				if(result != null){
					return true;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			logger.debug(e.getMessage(), e);
		}
		
		return false;
	}
	
	/**
	 * 判断value是否是一个整型
	 * @param value
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public static boolean isInteger(Object value){
		if(null == value){
			return false;
		}
		
		try{
			if(value instanceof Integer){
				return true;
			}else{
				Integer.parseInt(value.toString());
				return true;
			}
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	/**
	 * 判断整型value是否在minValue和maxValue之间
	 * @param value
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public static boolean isValidInteger(Integer value, Integer minValue, Integer maxValue){
		if(null == value){
			return false;
		}
		
		if(value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static boolean isDouble(Object value){
		if(null == value){
			return false;
		}
		
		try{
			if(value instanceof Double){
				return true;
			}else{
				Double.parseDouble(value.toString());
				return true;
			}
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	
	public static boolean isValidDouble(Double value, Integer minValue){
		if(null == value){
			return false;
		}
		
		if(value.compareTo(new Double(minValue)) >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static boolean isValidBigDecimal(BigDecimal value, int minValue, int maxValue) {
		if(null == value){
			return false;
		}
		
		if(value.compareTo(new BigDecimal(minValue)) >= 0 && value.compareTo(new BigDecimal(maxValue)) <= 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static boolean isValidString(Object value, Integer minLength, Integer maxLength){
		if(null == value){
			return false;
		}
		
		String result = null;
		if(value instanceof String){
			result = (String)value;
		}else{
			result = value.toString();
		}
		
		if(minLength.compareTo(result.length()) <= 0 && maxLength.compareTo(result.length()) >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isValidDate(String pattern, Object source){
		if(null == source){
			return false;
		}
		
		if(source instanceof Date){
			return true;
		}
		
		if(source instanceof String){
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			try{
				format.setLenient(false);
				format.parse(source.toString());
				return true;
			}catch(ParseException e){
				return false;
			}
		}
		
		return false;
	}

	
	public static boolean isCurrency(Object value, Integer minValue){
		if(value == null){
			return false;
		}
		
		String result = value.toString();
		if(result.length() > 12){
			return false;
		}
		
		if(Pattern.compile(AMT_CONSTRUCTURE).matcher(result).matches()){
			BigDecimal amount = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP);
			return amount.compareTo(new BigDecimal(minValue).setScale(2, RoundingMode.HALF_UP)) >= 0;
		}else{
			return false;
		}
	}
	
	
	public static boolean isEmail(Object email){
		if(email == null){
			return false;
		}

		return Pattern.compile(EMAIL_PATTERN).matcher(email.toString()).matches();
	}
	
	
	public static boolean isIdentity(Object identity){
		if(identity == null){
			return false;
		}
		
		return Pattern.compile(IDENTITY_PATTERN).matcher(identity.toString()).matches();
	}
	
	
	public static boolean isMobile(Object mobile){
		if(mobile == null){
			return false;
		}
		
		return Pattern.compile(MOBILE_PATTERN).matcher(mobile.toString()).matches();
	}
	
	public static boolean isUrl(Object value) {
		if(value == null){
			return false;
		}
		
        String regex = "(http|ftp|https)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        Pattern patternOne = Pattern.compile(regex);
        if (patternOne.matcher(value.toString()).matches()) {
            return true;
        }
        String regExThree = "(http|ftp|https)?://(\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b)+(/[\\w- ./?%&=]*)?";
        Pattern patternThree = Pattern.compile(regExThree);
        if (patternThree.matcher(value.toString()).matches()) {
            return true;
        }
        String regExFour = "(http|ftp|https)?://[A-Za-z]+(/[\\w- ./?%&=]*)?";
        Pattern patternFour = Pattern.compile(regExFour);
        if (patternFour.matcher(value.toString()).matches()) {
            return true;
        }
        String regExTwo = "(http|ftp|https)?://(\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b)+[:\\d]+(/[\\w- ./?%&=]*)?";
        Pattern patternTwo = Pattern.compile(regExTwo);
        if (patternTwo.matcher(value.toString()).matches()) {
            return true;
        }
        String regExFive = "(http|ftp|https)?://([\\w-]+\\.)+[\\w-]+[:\\d]+(/[\\w- ./?%&=]*)?";
        Pattern patternFive = Pattern.compile(regExFive);
        if (patternFive.matcher(value.toString()).matches()) {
            return true;
        }
        return false;
    }
	
	public static boolean checkExpression(String patternExp, Object value){
		if(value == null || patternExp == null){
			return false;
		}
		
		Pattern pattern = null;
		try{
			patternLock.lockInterruptibly();
			pattern = expressionPatternMap.get(patternExp);
			if(null == pattern){
				logger.debug("Firstly, compile pattern: {} and then cache it", new Object[]{patternExp});
				pattern = Pattern.compile(patternExp);
				expressionPatternMap.put(patternExp, pattern);
			}
		}catch(InterruptedException e){
			return false;
		}finally{
			patternLock.unlock();
		}
		
		return pattern.matcher(value.toString()).matches();
	}
	
}
