package com.stip.net.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.stip.net.oauth.service.SecurityService;

/**
 * 授权
 * @author cja
 *
 */
@Scope("request")
@RequestMapping("/oauth")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class AuthController {
	@Autowired
	public SecurityService securityService;
	@Autowired
	public TokenStore tokenStore;
	@Autowired
	private SessionLocaleResolver localeResolver;
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

    @PostMapping("/token")
    public Map<String,Object> token(HttpServletRequest request){
    	Map<String,Object> jsonResult=new HashMap<String, Object>(1);
		Locale locale = localeResolver.resolveLocale(request);
    	String appKey=request.getParameter("appKey");
    	String userKey=request.getParameter("userKey");
    	String userName=request.getParameter("userName");
    	
    	if(StringUtils.isNotEmpty(appKey)&&StringUtils.isNotEmpty(userKey)&&StringUtils.isNotEmpty(userName)) {
    		OAuth2AccessToken token=securityService.getAccessToken(appKey,tokenStore,userKey,userName);
    		jsonResult.put("token", token);
    	}else {
    		jsonResult.put("error",  messageSource.getMessage("errorAccess", null, locale));
    	}
        
        return jsonResult;
    }
	

}
