package com.greenatom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value ="")
public class CaseLabJavaCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseLabJavaCrmApplication.class, args);
    }

}
