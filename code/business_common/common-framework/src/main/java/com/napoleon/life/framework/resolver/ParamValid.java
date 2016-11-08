package com.napoleon.life.framework.resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamValid {
	
	/**
	 * 是否验证登陆人是否存在
	 * @return
	 */
	boolean validUser() default true;
}
