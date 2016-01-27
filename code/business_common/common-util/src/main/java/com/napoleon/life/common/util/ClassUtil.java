package com.napoleon.life.common.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {

	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	private static final Set<Class<?>> primitiveWrapperTypeSet = new HashSet<Class<?>>();

	static {
		primitiveWrapperTypeSet.add(Boolean.class);
		primitiveWrapperTypeSet.add(Byte.class);
		primitiveWrapperTypeSet.add(Character.class);
		primitiveWrapperTypeSet.add(Double.class);
		primitiveWrapperTypeSet.add(Float.class);
		primitiveWrapperTypeSet.add(Integer.class);
		primitiveWrapperTypeSet.add(Long.class);
		primitiveWrapperTypeSet.add(Short.class);
		primitiveWrapperTypeSet.add(String.class);
		primitiveWrapperTypeSet.add(Date.class);
		primitiveWrapperTypeSet.add(BigDecimal.class);
		primitiveWrapperTypeSet.add(Enum.class);
	}
	
	public static boolean isPrimitiveWrapper(Class<?> clazz){
		return primitiveWrapperTypeSet.contains(clazz);
	}
	
	public static boolean isPrimitiveOrWrapper(Class<?> clazz, Object value){
		return (clazz.isPrimitive() || isPrimitiveWrapper(clazz) || value instanceof Enum);
	}
	

    public static boolean isPrimitiveOrWrapperArray(Class<?> clazz) {
        return (clazz.isArray() && (clazz.getComponentType().isPrimitive() || isPrimitiveWrapper(clazz.getComponentType())));
    }
    
	public static Method getMethod(String methodName, Class<?> clazz,
			Class<?>... parameterTypes) {
		Method method = null;

		try {
			method = clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (Exception e) {
			logger.warn(String.format("can not find method: %s from class %s",
					methodName, clazz.getName()));
			try {
				method = clazz.getSuperclass().getDeclaredMethod(methodName,
						parameterTypes);
			} catch (Exception e1) {
				logger.warn(String.format(
						"can not find method: %s from class %s", methodName,
						clazz.getSuperclass()));
			}
		}

		return method;
	}

	public static Method findGetMethod(String fieldName, Class<?> clazz) {
		Method method = null;
		if (fieldName != null && clazz != null) {
			String methodSuffix = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getter = "get" + methodSuffix;
			method = getMethod(getter, clazz);

			if (method == null) {
				getter = "is" + methodSuffix;
				method = getMethod(getter, clazz);
			}
		}

		return method;
	}

}
