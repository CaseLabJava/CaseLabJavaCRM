package com.greenatom.clientselfservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class ClientSelfServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientSelfServiceApplication.class, args);
    }
}
