package com.stip.net.oauth;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lottery.net.utils.Constants;
import com.stip.net.service.UserIpService;

/**
 * security 认证
 */

/**
 */
@Configuration
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter {
    private static final String RESOURCE_ID = "REST_SERVICE";

    @Autowired
    private UserIpService userIpService;

    @Autowired
    DataSource shardingDataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(shardingDataSource);
    }

    public class CustomTokenEnhancer implements TokenEnhancer {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,OAuth2Authentication authentication) {
            Map<String, Object> additionalInfo = setUserInfo(authentication);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            
            return accessToken;
        }

        private Map<String, Object> setUserInfo(OAuth2Authentication authentication) {
            Map<String, Object> additionalInfo = new HashMap<>();
            
            return additionalInfo;
        }
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(new CustomTokenEnhancer())
                .approvalStoreDisabled();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients();
    }

    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("REST_SERVICE");
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                    .csrf()
                    .disable()
                    .headers()
                    .frameOptions().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/lottery/goBet.do**").hasRole("ADMIN")
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated();
            http.addFilterBefore(new AuthenticationProcessingFilter(userIpService), UsernamePasswordAuthenticationFilter.class);

        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(Constants.APP_KEY)//Mobile
                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes("authorization_code","password", "refresh_token")
                .authorities(Constants.ADMIN)
                .scopes("read", "write")
                .secret(Constants.APP_SECRET)
                .accessTokenValiditySeconds(604800);
    }


}