package com.cloud.oep.auth.config;

import com.cloud.oep.common.core.constant.AuthConstants;
import com.cloud.oep.common.core.constant.SecurityConstants;
import com.cloud.oep.common.core.utils.RedisUtils;
import com.cloud.oep.common.security.service.OepClientDetailsService;
import com.cloud.oep.common.security.service.OepUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zrf
 * @date 2021/3/11
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;
    @Resource(name = "authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    @Resource
    private OepUserServiceImpl oepUserService;
    @Resource
    private TokenEnhancer tokenEnhancer;
    @Resource
    private KeyPair keyPair;
    @Resource
    private RedisUtils redisUtils;



    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer);
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(tokenStore())
                //.tokenEnhancer(tokenEnhancer)//登录后，存储用户信息到token中
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(oepUserService)
                .authenticationManager(authenticationManager)
                // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                //      1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                .reuseRefreshTokens(true);
    }

    /**
     * 用来配置客户端详情服务,数据库模式
     **/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用JdbcClientDetailsService客户端详情服务
        OepClientDetailsService clientDetailsService = new OepClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter()) {
            /**
             * 设置token，并且添加redis
             */
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
                //非客户端模式
                if (!SecurityConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
                    //添加Jwt Token白名单,将Jwt以jti为Key存入redis中，并保持与原Jwt有一致的时效性
                    Map<String, Object> dataMap = token.getAdditionalInformation();
                    String jti = dataMap.get(AuthConstants.JWT_TOKEN_KEY).toString();
                    String userType = dataMap.get(AuthConstants.JWT_USER_TYPE).toString();
                    String userId = dataMap.get(AuthConstants.JWT_USER_Id).toString();
                    String key = AuthConstants.USER_ACCESS_TOKEN_PREFIX + userType + ":" + userId + ":" + jti;
                    redisUtils.set(key, 1, token.getExpiresIn());
                }
                super.storeAccessToken(token, authentication);
            }

            /**
             * 删除token，并且删除redis key
             */
            @Override
            public void removeAccessToken(OAuth2AccessToken token) {
                Map<String, Object> dataMap = token.getAdditionalInformation();
                if (dataMap.containsKey(AuthConstants.JWT_USER_Id)) {
                    String jti = dataMap.get(AuthConstants.JWT_TOKEN_KEY).toString();
                    String userType = dataMap.get(AuthConstants.JWT_USER_TYPE).toString();
                    String userId = dataMap.get(AuthConstants.JWT_USER_Id).toString();
                    String key = AuthConstants.USER_ACCESS_TOKEN_PREFIX + userType + ":" + userId + ":" + jti;
                    redisUtils.del(key);
                }
                super.removeAccessToken(token);
            }
        };
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair);
        return converter;
    }

}
