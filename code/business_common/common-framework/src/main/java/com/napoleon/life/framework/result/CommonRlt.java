package com.napoleon.life.framework.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class CommonRlt<T> implements Serializable {
    private static final long serialVersionUID = -5287593972225882740L;

    @JSONField(name = "rlt_code")
    private String code;// 响应码代码
  
    @JSONField(name = "rlt_msg")
    private String msg;// 响应码描述
 
    private T data;// 返回数据

    public CommonRlt() {
        super();
    }

    public CommonRlt(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonRlt(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String toJSONString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
