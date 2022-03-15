package com.hellonature.hellonature_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HellonatureBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellonatureBackApplication.class, args);
    }

}
