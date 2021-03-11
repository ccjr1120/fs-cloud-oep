package com.cloud.oep.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zrf
 * @date 2021/3/4
 */
@Service
public class OepUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.err.println(12312);
        return null;
    }

    public UserDetails loadUserByUsername(String username, Integer uType) throws UsernameNotFoundException {
        return null;
    }
}
