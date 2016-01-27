package com.napoleon.life.common.util.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.napoleon.life.common.util.StringUtil;

public class ContextUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ContextUtil.class);
	
	private static final class ThreadContext{
		public TreeMap<String, String> validatedSource = new TreeMap<String, String>();
		public TreeMap<String, Object> signedSource = new TreeMap<String, Object>();
	}
	
	private static final ThreadLocal<ThreadContext> serializedRequest = new ThreadLocal<ThreadContext>();
	
	private ContextUtil(){}
	
	private static ThreadContext getRequest(){
		ThreadContext context = serializedRequest.get();
		if(context == null){
			context = new ThreadContext();
			setRequest(context);
		}
		
		return context;
	}

	private static void setRequest(ThreadContext context) {
		serializedRequest.set(context);
	}
	
	public static void clear(){
		serializedRequest.remove();
	}
	
	public static String getValidatedSource(){
		logger.debug("validated source: {}", new Object[] {getRequest().validatedSource});
		return serializeSignedMap(getRequest().validatedSource);
	}
	
	public static boolean setValidatedSource(String key, String value){
		TreeMap<String, String> validatedMap = getRequest().validatedSource;
		return setIfAbsent(validatedMap, key, value);
	}
	
	public static String getSignedSource() {
        logger.debug("sign souce: {}", new Object[] {getRequest().signedSource.toString()});
        return serializeComplexSource(getRequest().signedSource);
    }
	
	public static boolean setSignedSource(String key, String val) {
        TreeMap<String, Object> signedMap = getRequest().signedSource;
        return setIfAbsent0(signedMap, key, val);
    }
	
	
	private static boolean setIfAbsent0(TreeMap<String, Object> container, String key, String val) {
        if (!container.containsKey(key)) {
            container.put(key, val);
        }
        else {
            Object value = container.get(key);
            List<String> arr = null;
            if (value instanceof List) {
                arr = (List<String>) value;
                arr.add(val);
            }
            else {
                arr = new ArrayList<String>();
                arr.add(String.valueOf(value));
                arr.add(val);
            }
            container.put(key, arr);
            logger.debug("Dup validated field: {}", new Object[] { key });
        }
        return true;
    }

	private static boolean setIfAbsent(TreeMap<String, String> validatableMap, String key, String val) {
        if (!validatableMap.containsKey(key)) {
            validatableMap.put(key, val);
            return true;
        }
        else {
            logger.debug("Omit override validated field: {}", new Object[] { key });
            return false;
        }
    }
	
	public static String serializeSignedMap(TreeMap<String, String> signedMap) {
        StringBuffer rc = new StringBuffer(1024);
        for (Entry<String, String> entry : signedMap.entrySet()) {
            String value = entry.getValue();
            if (value != null && value.length() > 0) {
                rc.append("&").append(entry.getKey().trim()).append("=").append(value);
            }
        }

        if (rc.length() > 0) {
            rc.deleteCharAt(0);
        }

        logger.debug("enc source: {}", new Object[] { rc });
        return rc.toString();
    }

    /**
     * Serialize complex Object(including List<String>) to idempotent source.
     *
     * @param params serialized Map
     * @return serialized idempotent source
     */
    public static String serializeComplexSource(TreeMap<String, Object> params) {
        StringBuffer rc = new StringBuffer(2048);
        for (Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (!StringUtil.isEmpty(value)) {
                if (value instanceof List) {
                    List<String> col = (List<String>) value;
                    Collections.sort(col);
                    for (String s : col) {
                        rc.append("&").append(entry.getKey().trim()).append("=").append(s);
                    }
                }
                else {
                    rc.append("&").append(entry.getKey().trim()).append("=").append(value);
                }
            }
        }

        if (rc.length() > 0) {
            rc.deleteCharAt(0);
        }
        
        logger.debug("enc source: {}", new Object[] { rc });
        return rc.toString();
    }
}
