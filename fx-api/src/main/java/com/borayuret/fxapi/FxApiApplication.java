package com.borayuret.fxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxApiApplication.class, args);
    }

}
