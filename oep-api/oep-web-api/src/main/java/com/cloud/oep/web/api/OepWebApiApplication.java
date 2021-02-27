package com.cloud.oep.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zrf
 * @date 2021/2/27
 */

@EnableDiscoveryClient
@SpringBootApplication
public class OepWebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OepWebApiApplication.class, args);
    }
}
