package com.jojo.gezginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication
@EnableCaching
public class GezginServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GezginServiceApplication.class, args);


    }

}
