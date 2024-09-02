package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Team12ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team12ProjectApplication.class, args);
    }

}
