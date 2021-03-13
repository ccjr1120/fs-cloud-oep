package com.cloud.oep.common.security.service;

import com.cloud.oep.common.core.constant.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author ccjr
 */
public class OepClientDetailsService extends JdbcClientDetailsService {
    public OepClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 支持缓存
     */
    @Override
    @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }
}
