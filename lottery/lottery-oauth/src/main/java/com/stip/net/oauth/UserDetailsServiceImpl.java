package com.stip.net.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stip.net.entity.LotteryUser;
import com.stip.net.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
	public UserService userService;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        LotteryUser user=userService.getUserByName(uid);
        
        if(null==user) {
        	throw new UsernameNotFoundException(uid);
        }else {
        	return new User(uid,user.getUserKey(), getAuthorities(4));
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 4) {
            roles.add("user");
            roles.add("admin");
        } else {
            roles.add("user");
        }

        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

        return authorities;
    }
}
