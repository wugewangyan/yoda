package com.napoleon.life.common.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

	/**
	 * 验证字段的描述
	 * @return
	 */
	String desc();
	
	/**
	 * 是否可以为空（默认不能为空）
	 * 如果可以为空就不需要校验
	 * @return
	 */
	boolean nullable() default false;
	
	/**
	 * 是否是整数
	 * @return
	 */
	boolean isInteger() default false;
	
	/**
	 * 是否是浮点数
	 * @return
	 */
	boolean isDouble() default false;
	
	/**
	 *  是否是货币
	 * @return
	 */
	boolean isCurrency() default false;
	
	/**
	 * 是否是日期
	 * @return
	 */
	boolean isDate() default false; 
	
	/**
	 * 日期的格式化方式
	 * @return
	 */
	String dateFormat() default "yyyy-MM-dd";
	
	
	/**
	 * 是否是邮箱账户
	 * @return
	 */
	boolean isEmail() default false;
	
	
	/**
	 * 是否是身份证
	 * @return
	 */
	boolean isIdentity() default false;
	
	
	/**
	 * 是否是电话号码
	 * @return
	 */
	boolean isPhone() default false;
	
	
	/**
	 * 是否是URL
	 * @return
	 */
	boolean isUrl() default false;
	
	/**
	 * 是否是某个枚举
	 * @return
	 */
	Class<?> enumScope() default Void.class;
	
	/**
	 * 通过方法验证
	 * @return
	 */
	String dependesOn() default "";
	
	
	/**
	 * 通过正则表达式校验
	 * @return
	 */
	String expression() default "";
	
	/**
	 * 字符串的最小长度
	 * @return
	 */
	int minLength() default 1;
	
	
	/**
	 * 字符串的最大长度
	 * @return
	 */
	int maxLength() default Integer.MAX_VALUE;
	
	
	/**
	 * 数字的最小值
	 * @return
	 */
	int minValue() default Integer.MIN_VALUE;
	
	
	/**
	 * 数字的最大值
	 * @return
	 */
	int maxValue() default Integer.MAX_VALUE;
	
	
	boolean isObject() default false;
	
	boolean isListObject() default false;
	
}
