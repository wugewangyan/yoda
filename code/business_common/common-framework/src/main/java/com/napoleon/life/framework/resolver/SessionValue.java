package com.napoleon.life.framework.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionValue {

	/**
	 * 需要在session范围中获取的值的key
	 * @return
	 */
	String key();
}
