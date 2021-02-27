package com.cloud.oep.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zrf
 * @date 2021/2/27
 */

@EnableDiscoveryClient
@SpringBootApplication
public class OepGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OepGatewayApplication.class, args);
    }

}
