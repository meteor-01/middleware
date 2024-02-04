package com.meteor.whitelisttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.meteor.*"})
public class WhiteListTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteListTestApplication.class, args);
    }

}
