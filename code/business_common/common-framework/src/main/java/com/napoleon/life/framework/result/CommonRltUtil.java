package com.napoleon.life.framework.result;

import com.napoleon.life.exception.CommonResultCode;

public class CommonRltUtil {
    /**
     * 通过code创建返回对象。
     *
     * @param code
     * @return
     */
    public static CommonRlt createCommonRlt(CommonResultCode code) {
        return new CommonRlt(code.getCode(), code.getChineseMessage());
    }

    /**
     * 通过code创建返回对象。
     *
     * @param code
     * @return
     */
    public static String createCommonRltToString(CommonResultCode code) {
        return new CommonRlt(code.getCode(), code.getChineseMessage()).toJSONString();
    }

    /**
     * 通过code,msg创建返回对象。
     *
     * @param code
     * @return
     */
    public static CommonRlt createCommonRlt(String code, String msg) {
        return new CommonRlt(code, msg);
    }
    
    /**
     * 通过code创建返回对象。
     *
     * @param code
     * @return
     */
    public static String createCommonRltToString(String code, String msg) {
        return new CommonRlt(code, msg).toJSONString();
    }

    /**
     * 通过code, data创建返回对象。
     *
     * @param code
     * @return
     */
    public static CommonRlt createCommonRlt(CommonResultCode code, Object data) {
        return new CommonRlt(code.getCode(), code.getChineseMessage(), data);
    }
    
    
    /**
     * 通过code创建返回对象。
     *
     * @param code
     * @return
     */
    public static String createCommonRltToString(CommonResultCode code, Object data) {
        return new CommonRlt(code.getCode(), code.getChineseMessage(), data).toJSONString();
    }

    /**
     * 通过code,msg, data创建返回对象。
     *
     * @param code
     * @return
     */
    public static CommonRlt createCommonRlt(String code, String msg, Object data) {
        return new CommonRlt(code, msg, data);
    }
    
    /**
     * 通过code创建返回对象。
     *
     * @param code
     * @return
     */
    public static String createCommonRltToString(String code, String msg, Object data) {
        return new CommonRlt(code, msg, data).toJSONString();
    }
}
