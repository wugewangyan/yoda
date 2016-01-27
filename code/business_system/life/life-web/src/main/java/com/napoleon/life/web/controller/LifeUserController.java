package com.napoleon.life.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.napoleon.life.common.util.CryptUtil;
import com.napoleon.life.common.util.RSAUtils;
import com.napoleon.life.common.util.StringUtil;
import com.napoleon.life.common.util.exception.LifeUtilException;
import com.napoleon.life.common.util.validator.BaseValidator;
import com.napoleon.life.core.dto.LifeResponseDto;
import com.napoleon.life.core.dto.LifeUserLoginDto;
import com.napoleon.life.core.dto.LifeUserRegisterDto;
import com.napoleon.life.core.dto.LifeUserResetPasswordDto;
import com.napoleon.life.core.entity.LifeUser;
import com.napoleon.life.core.enums.MessageTypesEnum;
import com.napoleon.life.core.enums.StatusEnum;
import com.napoleon.life.core.exception.LifeException;
import com.napoleon.life.core.exception.LifeExceptionEnum;
import com.napoleon.life.core.service.LifeUserService;
import com.napoleon.life.web.interceptor.AuthAccess;
import com.napoleon.life.web.interceptor.CookieUtil;

@Controller
public class LifeUserController {
	
	private static final Logger logger = LoggerFactory.getLogger(LifeUserController.class);

	@Autowired
	private LifeUserService lifeUserService;

	@Autowired
	private Producer captchaProducer;
	
	/**
	 * 注册
	 * @param register
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(LifeUserRegisterDto register, HttpServletRequest request, Model model) {
		try{
			String kaptchaKey = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			register.setSessionScopeIdentifyCode(kaptchaKey);
			
			//解密页面传递的密码
			register.setPassword(RSAUtils.decryptStringByJs(register.getPassword()));
			register.setConfirmPassword(RSAUtils.decryptStringByJs(register.getConfirmPassword()));
			String errors = BaseValidator.validate(register, false);
			
			if(StringUtil.isEmpty(errors)){
				LifeUser user = new LifeUser();
				user.setUserName(register.getUserName());
				user.setEmail(register.getEmail());
				user.setPassword(register.getPassword());
				this.lifeUserService.userRegister(user);
				
				model.addAttribute("email", user.getEmail());
				model.addAttribute("index", MessageTypesEnum.REGISTER_SUCCESS.getCode());
			}else{
				model.addAttribute("index", MessageTypesEnum.REGISTER_FAIL.getCode());
				model.addAttribute("errorMsg", errors);
			}
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("index", MessageTypesEnum.REGISTER_FAIL.getCode());
			model.addAttribute("errorMsg", e.getErrChineseMsg());
		}
		
		return "messages";
    }
	
	
	/**
	 * 登陆
	 * @param login
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(LifeUserLoginDto login, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			login.setP(RSAUtils.decryptStringByJs(login.getP()));
			String errors = BaseValidator.validate(login, false);
			if(StringUtil.isEmpty(errors)){
				LifeUser lifeUser = this.lifeUserService.validLogin(login.getE(), login.getP());
				
				// 如果记住密码
				if(login.getRememberMe() != null && "on".equals(login.getRememberMe())){
					login.setP(lifeUser.getPassword()); // 传递加密之后的Password
					CookieUtil.saveCookie(login, response);
				}else{
					CookieUtil.clearCookie(response);
				}
				
				HttpSession session = request.getSession();
				session.setAttribute(com.napoleon.life.core.util.Constants.USER_SESSION_NAME, lifeUser);
				String r = CryptUtil.encrypt(login.getE(), com.napoleon.life.core.util.Constants.COOKIE_SECURE_KEY);
				model.addAttribute("r", r);
				return "redirect:index";
			}else{
				model.addAttribute("errorMsg", errors);
			}
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
			model.addAttribute("errorCode", e.getErrCode());
		}catch(LifeUtilException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
		}
		
		model.addAttribute("index", MessageTypesEnum.LOGIN_FAIL.getCode());
		return "messages";
    }
	
	
	@RequestMapping(value = "/login-out", method = RequestMethod.GET)
    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(com.napoleon.life.core.util.Constants.USER_SESSION_NAME);
		String contextPath = request.getContextPath();
		try {
			response.sendRedirect(contextPath + "/login.html");
		} catch (IOException e) {
		}//登陆
    }
	
	
	
	@AuthAccess
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(String r, Model model) {
		try{
			String email = CryptUtil.decrypt(r, com.napoleon.life.core.util.Constants.COOKIE_SECURE_KEY);
			return "index";
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
			model.addAttribute("errorCode", e.getErrCode());
		}catch(LifeUtilException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
		}
		
		model.addAttribute("index", MessageTypesEnum.LOGIN_FAIL.getCode());
		return "messages";
    }
	
	
	/**
	 * 找回密码
	 * @param login
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String findPassword(String email, Model model) {
		try{
			this.lifeUserService.sendResetPasswordEmail(email);
			model.addAttribute("email", email);
			return "redirect:enter-reset";
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
			model.addAttribute("index", MessageTypesEnum.FIND_PASSWORD_FAIL.getCode());
		}
		
		return "messages";
    }
	
	
	/**
	 * 进入确认找回密码页面
	 * @param u
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/enter-reset", method = RequestMethod.GET)
	public String enterResetConfirm(String email, Model model){
		model.addAttribute("email", email);
		return "confirm-reset";
	}
	
	/**
	 * 找回密码,确认步骤
	 * @param login
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/confirm-reset", method = RequestMethod.POST)
    public String confirmPassword(LifeUserResetPasswordDto reset, Model model) {
		try{
			//解密页面传递的密码
			reset.setPassword(RSAUtils.decryptStringByJs(reset.getPassword()));
			reset.setConfirmPassword(RSAUtils.decryptStringByJs(reset.getConfirmPassword()));
			String errors = BaseValidator.validate(reset, false);
			if(StringUtil.isEmpty(errors)){
				this.lifeUserService.confirmPassword(reset);
				model.addAttribute("index", MessageTypesEnum.RESET_PASSWORD_SUCCESS.getCode());
			}else{
				model.addAttribute("errorMsg", errors);
				model.addAttribute("index", MessageTypesEnum.RESET_PASSWORD_FAIL.getCode());
			}
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
			model.addAttribute("index", MessageTypesEnum.RESET_PASSWORD_FAIL.getCode());
		}catch(LifeUtilException e){
			logger.error(e.getErrChineseMsg(), e);
			model.addAttribute("errorMsg", e.getErrChineseMsg());
			model.addAttribute("index", MessageTypesEnum.RESET_PASSWORD_FAIL.getCode());
		}
		
		model.addAttribute("email", reset.getEmail());
		return "messages";
    }
	
	/**
	 * 使用邮箱激活账户
	 * @param a
	 * @param u
	 * @return
	 */
	@RequestMapping(value = "/activateAccount", method = RequestMethod.GET)
    public String activateAccount(String a, String u, Model model) {
		try{
			lifeUserService.activateAccount(u, a);
			model.addAttribute("index", MessageTypesEnum.VALIDATE_SUCCESS.getCode());
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			if(LifeExceptionEnum.ACCOUNT_HAS_ACTIVATE.getCode().equals(e.getErrCode())){
				// 说明发送邮件之前账户已经被激活
				model.addAttribute("index", MessageTypesEnum.VALIDATE_SUCCESS.getCode());
			}else{
				model.addAttribute("errorMsg", e.getErrChineseMsg());
				model.addAttribute("index", MessageTypesEnum.VALIDATE_FAIL.getCode());
			}
		}
		
		return "messages";
    }
	
	
	/**
	 * 发送激活邮件
	 * @param email
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resendActivateEmail", method = RequestMethod.GET)
    public String resendActivateEmail(String email, Model model) {
		try{
			lifeUserService.resendActivateEmail(email);
			model.addAttribute("index", MessageTypesEnum.EMAIL_SEND_SUCCESS.getCode());
		}catch(LifeException e){
			logger.error(e.getErrChineseMsg(), e);
			if(LifeExceptionEnum.ACCOUNT_HAS_ACTIVATE.getCode().equals(e.getErrCode())){
				// 说明发送邮件之前账户已经被激活
				model.addAttribute("index", MessageTypesEnum.VALIDATE_SUCCESS.getCode());
			}else{
				model.addAttribute("errorMsg", e.getErrChineseMsg());
				model.addAttribute("index", MessageTypesEnum.EMAIL_SEND_FAIL.getCode());
			}
		}
		
		model.addAttribute("email", email);
        return "messages";
    }
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/captcha-image")  
    public ModelAndView captchaImage(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {  
   
        response.setDateHeader("Expires", 0); 
 
        // Set standard HTTP/1.1 no-cache headers. 
 
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader). 
 
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        // Set standard HTTP/1.0 no-cache header. 
 
        response.setHeader("Pragma", "no-cache");  
        // return a jpeg 
 
        response.setContentType("image/jpeg");  
        // create the text for the image
  
        String capText = captchaProducer.createText();  
        // store the text in the session 
 
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        // create the image with the text  

        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        // write the data out  

        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    } 
	
	
	/**
	 * 校验验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/validate-captcha")  
    public LifeResponseDto validateCaptcha(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {  
		String identifyCode = request.getParameter("identifyCode");
 
        String kaptchaKey = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY); 
        
        LifeResponseDto responseDto = new LifeResponseDto();
        if(identifyCode != null && identifyCode.equalsIgnoreCase(kaptchaKey)){
        	responseDto.setStatus(StatusEnum.STATUS_SUCCESS.getCode());
        }else{
        	responseDto.setStatus(StatusEnum.STATUS_FAIL.getCode());
        	responseDto.setErrorMsg("验证码不正确");
        }
        
        return responseDto;  
    } 
	
	/**
	 * 校验邮箱账户是否存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/validate-email")  
    public LifeResponseDto validateEmail(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {  
		String email = request.getParameter("email");
		LifeUser lifeUser = this.lifeUserService.findByEmail(email);
        
        LifeResponseDto responseDto = new LifeResponseDto();
        if(lifeUser == null){
        	responseDto.setStatus(StatusEnum.STATUS_SUCCESS.getCode());
        	responseDto.setErrorMsg("该邮箱账户不存在");
        }else{
        	responseDto.setStatus(StatusEnum.STATUS_FAIL.getCode());
        	responseDto.setErrorMsg("该邮箱账户已存在");
        }
        
        return responseDto;  
    } 
	
	/**
	 * 获取非对称加密信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getKeyPair")  
	public Map<String, String> getKeyPair(){
		Map<String, String> result = new HashMap<String, String>();
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		
		String publicExponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
		String publicModulus = new String(Hex.encodeHexString(publicKey.getModulus().toByteArray()));
		
		result.put("publicExponent", publicExponent);
		result.put("publicModulus", publicModulus);
		return result;
	}
}
