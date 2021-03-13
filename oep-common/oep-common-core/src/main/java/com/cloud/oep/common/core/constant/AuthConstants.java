package com.cloud.oep.common.core.constant;

/**
 * auth 常量
 * @author zrf
 */
public interface AuthConstants {

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * jwt token key
     */
    String JWT_TOKEN_KEY = "jti";

    /**
     * jwt 用户id
     */
    String JWT_USER_Id = "uid";

    /**
     * jwt 用户类别
     */
    String JWT_USER_TYPE = "uType";

    /**
     * 用户token缓存
     */
    String USER_ACCESS_TOKEN_PREFIX = "oep_oauth:auth_to_access:";

}
