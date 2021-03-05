package com.cloud.oep.auth.security;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.oep.common.security.entity.OepUser;
import com.cloud.oep.common.security.service.OepUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zrf
 * @date 2021/3/4
 */
@Component
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final static String U_TYPE = "uType";

    private final OepUserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    public AppAuthenticationProvider(OepUserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println("2112321");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        Map<String, String> details = getAuthenticDetail(authentication);
        //帐号类别
        String userType = details.get(U_TYPE);
        if (StringUtils.isEmpty(userType)) {
            throw new UsernameNotFoundException("user_type is not null");
        }
        OepUser userDetails = (OepUser) userService.loadUserByUsername("username", Integer.parseInt(userType));
        //密码验证
        boolean check = passwordEncoder.matches(password, userDetails.getPassword());
        if (!check) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Map<String, String> getAuthenticDetail(Authentication authentication) {
        Object data = authentication.getDetails();
        Map<String, String> result = new HashMap<>();
        BeanUtil.copyProperties(data, result);
        return result;
    }

}
