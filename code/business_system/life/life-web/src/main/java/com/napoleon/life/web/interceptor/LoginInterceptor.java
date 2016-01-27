package com.napoleon.life.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.napoleon.life.common.util.CryptUtil;
import com.napoleon.life.common.util.EncryptUtil;
import com.napoleon.life.core.entity.LifeUser;
import com.napoleon.life.core.service.LifeUserService;
import com.napoleon.life.core.util.Constants;

@Component("loginInterceptor")
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private LifeUserService lifeUserService;
	
	private List<String> paths;

	public boolean preHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler) throws Exception {
		
		HttpServletRequest request = (HttpServletRequest)req; 
		HttpServletResponse response = (HttpServletResponse)resp; 

		//如果封装的user不为空,说明已经登陆,则继续执行用户的请求.下面的就不处理了
		if(this.validateLogin(request, response, handler)){
			return true;
		}else{
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login.html");//登陆
			return false;
		}
	}
	
	
	private boolean validateLogin(HttpServletRequest request, HttpServletResponse response, Object handler){
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			AuthAccess authAccess = handlerMethod.getMethodAnnotation(AuthAccess.class);
			if(authAccess == null){
				return true;
			}else{
				HttpSession session = request.getSession(true); 
				LifeUser user = (LifeUser)session.getAttribute(Constants.USER_SESSION_NAME);
				if(user != null){
					return true;
				}
				
				//user为空，说明用户还没有登陆,就尝试得到浏览器传送过来的Cookie
				// 根据cookieName取cookieValue
				Cookie cookies[] = request.getCookies();
				String cookieValue = null;
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						if (Constants.COOKIE_NAME.equals(cookies[i].getName())) {
							cookieValue = cookies[i].getValue();
							break;
						}
					}
				}
				
				// 如果cookieValue为空,返回
				if (cookieValue == null) {
					return false;
				}
				
				// 如果cookieValue不为空,才执行下面的代码
				// 先得到的CookieValue进行解码
				String cookieValueAfterDecode = CryptUtil.decrypt(cookieValue, Constants.COOKIE_SECURE_KEY);

				// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
				String cookieValues[] = cookieValueAfterDecode.split(":");
				if (cookieValues.length != 3) {
					return false;
				}

				// 判断是否在有效期内,过期就删除Cookie
				long validTimeInCookie = new Long(cookieValues[1]);
				if (validTimeInCookie < System.currentTimeMillis()) {
					// 删除Cookie
					CookieUtil.clearCookie(response);
					return false;
				}

				// 取出cookie中的用户名,并到数据库中检查这个用户名,
				String email = cookieValues[0];
				// 根据用户名到数据库中检查用户是否存在
				LifeUser lifeUser = this.lifeUserService.findByEmail(email);

				// 如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
				if (lifeUser != null) {
					String md5ValueInCookie = cookieValues[2];
					String md5ValueFromUser = EncryptUtil.MD5Encode(lifeUser.getEmail() + ":"
							+ lifeUser.getPassword() + ":" + validTimeInCookie + ":" + Constants.COOKIE_SECURE_KEY, "UTF8");
					// 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
					if (md5ValueFromUser.equals(md5ValueInCookie)) {
						session.setAttribute(Constants.USER_SESSION_NAME, lifeUser);
						return true;
					}else{
						return false;
					}
				} else {
					return false;
				}
			}
		}else{
			return false;
		}
		
		
		
		
	}


	public List<String> getPaths() {
		return paths;
	}


	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
}