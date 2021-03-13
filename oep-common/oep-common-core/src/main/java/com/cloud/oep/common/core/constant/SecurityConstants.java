package com.cloud.oep.common.core.constant;

public interface SecurityConstants {

    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from sys_oauth_client_details";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * 资源服务器默认bean名称
     */
    String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    /**
     * OAUTH URL
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 刷新
     */
    String REFRESH_TOKEN = "refresh_token";
}
