package com.stip.net.oauth;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.lottery.net.utils.IPAddressUtil;
import com.stip.net.entity.UserIp;
import com.stip.net.example.UserIpExample;
import com.stip.net.service.UserIpService;

/**
 * @Package: com.stip.net.oauth
 * @Description:
 * @Author: cgnet05
 * @CreatDate: 2018/12/19
 */
@Component
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public UserIpService userIpService;

    public AuthenticationProcessingFilter(UserIpService userIpService) {
        super(new AntPathRequestMatcher("/**"));
        this.userIpService = userIpService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!requiresAuthentication(req, res)) {
            chain.doFilter(request, response);
            return;
        }
        if (this.blackIpList(req)) {
            chain.doFilter(req, res);
            return;
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "访问被禁止");
//            throw new AuthenticationServiceException("IP has no access rights");
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        return null;
    }

    public boolean blackIpList(HttpServletRequest request) {
        String ip = IPAddressUtil.getClientIpAddress(request);
        UserIpExample example = new UserIpExample();
        example.createCriteria().andLoginIpEqualTo(ip);
        List<UserIp> ipList = userIpService.selectByExample(example);
        String userName = request.getParameter("userName");
        if (ipList == null || ipList.size() == 0) {
            UserIp userIp = new UserIp();
            userIp.setUserName(userName);
            userIp.setLoginIp(ip);
            userIp.setIpStatus("1");
            userIp.setCreateTime(new Date());
            userIpService.insert(userIp);
        } else {
            // 黑名单 拒绝
            if (!"1".equals(ipList.get(0).getIpStatus())) {
                return false;
            }
        }
        return true;
    }
}
