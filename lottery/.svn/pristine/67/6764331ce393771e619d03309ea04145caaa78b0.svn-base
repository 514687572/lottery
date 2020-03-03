package com.stip.net.oauth.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.lottery.net.utils.Constants;

@Service
public class SecurityService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String RESOURCE_ID = "REST_SERVICE";

    private SecurityService() {
    }

    public static OAuth2AccessToken getMobileAccessToken(String appKey, TokenStore tokenStore,String password, final String username) {
    	Map<String, String> requestParameters = new HashMap<>();
    	Set<String> scope = new HashSet<>();
    	Set<String> responseTypes = new HashSet<>();
    	Map<String, Serializable> extensionProperties = new HashMap<>();
    	Set<GrantedAuthority> authorities = new HashSet<>();
    	authorities.add(new SimpleGrantedAuthority(Constants.ADMIN));
        String clientId = appKey;

        requestParameters.put("username", username);
        requestParameters.put("grant_type", "password");
        requestParameters.put("password", password);

        scope.add("read");
        scope.add("write");

        Set<String> resourceIds = new HashSet<>();
        resourceIds.add(RESOURCE_ID);

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities,
                true, scope, resourceIds, null, responseTypes, extensionProperties);

        User userPrincipal = new User(username, password, true, true, true, true, authorities);

        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        authenticationToken.setDetails(requestParameters);

        TokenEnhancer tokenEnhancer = (OAuth2AccessToken accessToken, OAuth2Authentication authentication) -> {
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
            Map<String, Object> additionalInformation = new LinkedHashMap<>();
            additionalInformation.put("uid", username);
            token.setAdditionalInformation(additionalInformation);

            return accessToken;
        };

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds(604800);
        defaultTokenServices.setTokenEnhancer(tokenEnhancer);

        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        OAuth2AccessToken token = defaultTokenServices.createAccessToken(auth);
        
        return token;
    }

    public OAuth2AccessToken getAccessToken(String appkey,TokenStore tokenStore,String password,final String uid) {
        OAuth2AccessToken accessToken = getMobileAccessToken(appkey, tokenStore, password, uid);
        
        return accessToken;
    }
}

