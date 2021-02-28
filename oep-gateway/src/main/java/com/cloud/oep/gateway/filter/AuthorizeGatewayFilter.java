package com.cloud.oep.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author ling
 * @date 2021/2/28
 */
@Component
public class AuthorizeGatewayFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            System.err.println("进入过滤器");
            return chain.filter(exchange);
        });
    }

}
