package com.cloud.oep.common.security.entity;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author zrf
 * @date 2021/3/4
 */
@Getter
public class OepUser extends User {

    /**
     * 用户ID
     */
    @Getter
    private String uid;

    /**
     * 用户类型
     */
    private String uType;

    public OepUser(String uid, String username, String password, String uType, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
