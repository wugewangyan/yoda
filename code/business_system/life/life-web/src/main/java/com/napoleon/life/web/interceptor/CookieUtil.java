package com.napoleon.life.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.napoleon.life.common.util.CryptUtil;
import com.napoleon.life.common.util.EncryptUtil;
import com.napoleon.life.core.dto.LifeUserLoginDto;
import com.napoleon.life.core.util.Constants;

/*

 * 2007.09.21 by lyhapple

 * */
public class CookieUtil {

	// 保存Cookie到客户端--------------------------------------------------------------------------------------------------------
	// 在CheckLogonServlet.java中被调用
	// 传递进来的user对象中封装了在登陆时填写的用户名与密码
	public static void saveCookie(LifeUserLoginDto lifeUser, HttpServletResponse response) {

		// cookie的有效期
		long validTime = System.currentTimeMillis() + (Constants.COOKIE_MAX_AGE * 1000);
		// MD5加密用户详细信息
		String cookieValueWithMd5 = EncryptUtil.MD5Encode(lifeUser.getE() + ":" + lifeUser.getP()
				+ ":" + validTime + ":" + Constants.COOKIE_SECURE_KEY, "UTF8");
		// 将要被保存的完整的Cookie值
		String cookieValue = lifeUser.getE() + ":" + validTime + ":" + cookieValueWithMd5;
		// 再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = CryptUtil.encrypt(cookieValue, Constants.COOKIE_SECURE_KEY);
		// 开始保存Cookie
		Cookie cookie = new Cookie(Constants.COOKIE_NAME, cookieValueBase64);

		// 存两年(这个值应该大于或等于validTime)
		cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
		// cookie有效路径是网站根目录
		cookie.setPath("/");
		// 向客户端写入
		response.addCookie(cookie);

	}

	

	// 用户注销时,清除Cookie,在需要时可随时调用------------------------------------------------------------
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(Constants.COOKIE_NAME, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
