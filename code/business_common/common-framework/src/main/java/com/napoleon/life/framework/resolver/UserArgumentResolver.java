package com.napoleon.life.framework.resolver;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONObject;
import com.napoleon.life.common.util.StringUtil;
import com.napoleon.life.common.util.validator.BaseValidator;
import com.napoleon.life.exception.CommonException;
import com.napoleon.life.exception.CommonResultCode;
import com.napoleon.life.framework.base.BaseDto;
import com.napoleon.life.framework.redis.RedisServer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private RedisServer redisServer;
	
	@Value("${redis.wonderfull.life.access_token.expire}")
	private String wLifeAccessTokenRedisExpire;

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ParamValid.class);
    }

    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        String name = ModelFactory.getNameForParameter(parameter);
        Object attribute = BeanUtils.instantiate(parameter.getParameterType());

        WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
        if (binder.getTarget() != null) {
        	this.bindRequestParameters(binder, webRequest);
        	String errors = BaseValidator.validate(binder.getTarget(), false);
            if (!StringUtil.isEmpty(errors)) {
                throw new CommonException(CommonResultCode.ACCOUNT_HAS_ACTIVATE, errors);
            }
        }

        Object obj = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
        ParamValid paramValid = parameter.getMethodAnnotation(ParamValid.class);
        if(paramValid == null || paramValid.validUser()){
        	this.addUserInfo(webRequest, obj);
        }
        
        return obj;
    }

    private void addUserInfo(NativeWebRequest webRequest, Object attribute) {
        if (attribute instanceof BaseDto) {
            String access_token = webRequest.getParameter("access_token");
            String userJsonInfo = this.redisServer.get(access_token, Integer.valueOf(this.wLifeAccessTokenRedisExpire));
        	if(StringUtil.notEmpty(userJsonInfo)){
        		JSONObject user = JSONObject.parseObject(userJsonInfo);
        		((BaseDto)attribute).setUserNo(user.getString("user_no"));
                ((BaseDto)attribute).setUserName(user.getString("user_name"));
                ((BaseDto)attribute).setPhone(user.getString("phone"));
        	}else{
        		throw new CommonException(CommonResultCode.ACCOUNT_HAS_ACTIVATE);
        	}
        }
    }

    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request.getNativeRequest(HttpServletRequest.class);
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(httpRequest);
        
        // 将session范围内的值赋予target
        Field[] declaredFields = servletBinder.getTarget().getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			SessionValue sessionValue = field.getAnnotation(SessionValue.class);
			if(sessionValue != null){
				String key = sessionValue.key();
				Object value = httpRequest.getSession().getAttribute(key);
				field.setAccessible(true);
				field.set(servletBinder.getTarget(), value);
			}
		}
    }
}