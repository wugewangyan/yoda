package com.napoleon.life.common.util.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.napoleon.life.common.util.ClassUtil;
import com.napoleon.life.common.util.StringUtil;
import com.napoleon.life.common.util.context.ContextUtil;
import com.napoleon.life.exception.CommonException;

public class BaseValidator {

	public static class ValidateResult {
		private String errMsg;
		private String errCode;

		public ValidateResult(String errCode, String errMsg) {
			this.errCode = errCode;
			this.errMsg = errMsg;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

		public String getErrCode() {
			return errCode;
		}

		public void setErrCode(String errCode) {
			this.errCode = errCode;
		}
	}
	
	private static String serializeErr(List<ValidateResult> validateResults) {
		if (null == validateResults || validateResults.isEmpty()) {
            return null;
        }

        StringBuffer rc = new StringBuffer(1024);
        for (ValidateResult err : validateResults) {
            rc.append(err.getErrMsg()).append(",");
        }

        if (rc.length() > 0) {
            rc.deleteCharAt(rc.length() - 1);
        }

        return rc.toString();
	}
	
	
	public static boolean doValidate(Object validatedObject, Class<?> clazz,
			String fieldName, Object value, Validator validator,
			List<ValidateResult> results) {

		// 一. 校验值为空的情况
		if (value == null) {
			if (validator.nullable()) {
				return true;
			} else {
				results.add(new ValidateResult(fieldName, validator.desc() + "不能为空"));
				return false;
			}
		}

		// 二. 校验dependesOn所指定的方法是否返回true，如果返回false，则校验不通过
		if (StringUtil.notEmpty(validator.dependesOn()) && !ValidatorUtil.checkdependesOn(validator.dependesOn(), clazz, validatedObject)) {
			results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，dependesOn所指定的方法不存在或该方法返回失败"));
			return false;
		}
		
		return doValidatePrimitive(clazz, fieldName, value, validator, results);
	}

	/**
	 * 对某个原生类型或原生数组类型或原生集合类型的字段进行校验
	 * @param fieldName
	 * @param value
	 * @param validator
	 * @param results
	 * @return
	 */
	public static boolean doValidatePrimitive(Class<?> clazz, String fieldName, Object value,
			Validator validator, List<ValidateResult> results) {
		// 1. 校验原生集合类型
		if (value instanceof Collection) {
			Collection collection = (Collection) value;
			Object[] colls = collection.toArray();
			for (Object obj : colls) {
				boolean result = doValidatePrimitive(obj.getClass(), fieldName, obj, validator, results);
				if (!result) {
					return false;
				}
			}
		}
		
		// 四. 校验原生数组类型
		if (clazz.isArray()) {
			Object[] arrays = (Object[]) value;
			for (Object obj : arrays) {
				boolean result = doValidatePrimitive(obj.getClass(), fieldName, obj, validator, results);
				if (!result) {
					return false;
				}
			}
		}
		
		// 1. 校验该字段的值是否是指定某个枚举的值
		if (validator.enumScope() != null
				&& !validator.enumScope().equals(Void.class)
				&& !ValidatorUtil.checkenumScope(validator.enumScope(), value)) {
			results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不能被转换成指定的枚举"));
			return false;
		}

		if (value instanceof Integer || value instanceof Long || validator.isInteger()){
			if(!ValidatorUtil.isInteger(value)){
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的整数"));
				return false;
			}else{
				if(!ValidatorUtil.isValidInteger(Integer.parseInt(value.toString()), validator.minValue(), validator.maxValue())){
					results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，其取值范围应为:["
													+ validator.minValue() + ", " + validator.maxValue() + "]"));
					return false;
				}
			}
		}

		if (value instanceof Double || validator.isDouble()){
				if(!ValidatorUtil.isDouble(value)){
					results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的浮点型"));
					return false;
				}else{
					if(!ValidatorUtil.isValidDouble(Double.parseDouble(value.toString()), validator.minValue())){
						results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，其最小值应大于等于" + validator.minValue()));
						return false;
					}
				}
		}
		
		if ((value instanceof Date || value instanceof Timestamp || validator.isDate())
				&& !ValidatorUtil.isValidDate(validator.dateFormat(), value)) {
			results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的日期类型"));
			return false;
		}
		
		if(value instanceof BigDecimal && !ValidatorUtil.isValidBigDecimal((BigDecimal)value, validator.minValue(), validator.maxValue())){
			results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，其取值范围应为:["
								+ validator.minValue() + ", " + validator.maxValue() + "]"));
			return false;
		}

		if(value instanceof String){
			if (!ValidatorUtil.isValidString(value, validator.minLength(), validator.maxLength())) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，其长度范围应为:["
													+ validator.minLength() + ", " + validator.maxLength() + "]"));
				return false;
			}
			
			if (validator.isCurrency()
					&& !ValidatorUtil.isCurrency(value, validator.minValue())) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，金额类型的长度不能大于12，且最小值应大于等于" + validator.minValue()));
				return false;
			}
			
			if (validator.isEmail() && !ValidatorUtil.isEmail(value)) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的邮箱账户"));
				return false;
			}
	
			if (validator.isIdentity() && !ValidatorUtil.isIdentity(value)) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的身份证号"));
				return false;
			}
	
			if (validator.isPhone() && !ValidatorUtil.isMobile(value)) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的手机号"));
				return false;
			}
	
			if (validator.isUrl() && !ValidatorUtil.isUrl(value)) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，不是一个合法的URL"));
				return false;
			}
	
			if (StringUtil.notEmpty(validator.expression())
					&& !ValidatorUtil.checkExpression(validator.expression(), value)) {
				results.add(new ValidateResult(fieldName, validator.desc() + "校验失败，指定的正则表达式无法匹配成功"));
				return false;
			}
		}

		return true;
	}
	
	public static String validate(Object validatedObj, boolean entrance)throws CommonException{
		List<ValidateResult> validateResults = new ArrayList<ValidateResult>();
		validate(validatedObj, validatedObj.getClass(), validateResults, entrance);
		return serializeErr(validateResults);
	}
	

	public static void validate(Object validatedObj, Class<?> validatedClazz,
			List<ValidateResult> validateResults, boolean entrance)
			throws CommonException {
		if (null == validatedObj || null == validateResults) {
			throw new CommonException(
					"The validated Object and ErrorResult Container can not be null.");
		}

		// 获取该类所有的声明的字段，不包括继承的字段
		Field[] declaredFields = validatedClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			Validator validator = field.getAnnotation(Validator.class);
			if (validator != null) {
				// 说明该字段需要校验
				Method method = ClassUtil.findGetMethod(field.getName(),
						validatedClazz);
				if (method != null) {
					Object value = null;
					try {
						value = method.invoke(validatedObj);
							
						// 对原生类型或原生集合类型进行校验
						if(validator.isObject()){
							validate(value, value.getClass(), validateResults, entrance);
						}else if(validator.isListObject()){
							Object[] objects = null;
							if(value instanceof Collection){
								Collection rawCollection = (Collection)value;
								objects = rawCollection.toArray();
							}else{
								objects = (Object[])value;
							}
							
							for(int i = 0; i < objects.length; i++){
								validate(objects[i], objects[i].getClass(), validateResults, entrance);
							}
						}else{
							boolean result = doValidate(validatedObj, validatedClazz, field.getName(), value, validator, validateResults);
							if(result){
								if(entrance){
									Signable signable = field.getAnnotation(Signable.class);
									if(signable != null && value != null){
										ContextUtil.setSignedSource(field.getName(), value.toString());
									}
								}
							}
						}
					} catch (Exception e) {
//						throw new ValidatorException(
//								String.format(
//										"cannot invoke the method %s for the Object %s",
//										method.getName(), validatedObj));
						throw new CommonException(e);
					}
				} else {
					throw new CommonException(String.format(
							"Could not find the getter method: [%s]",
							field.getName()));
				}
			}
		}

		// Do recursive to retrieve all validatable fields
		Class<?> superClass = validatedClazz.getSuperclass();
		if (superClass != null && !Object.class.equals(superClass)) {
			validate(validatedObj, superClass, validateResults, entrance);
		}
	}

}
