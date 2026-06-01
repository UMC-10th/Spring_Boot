package com.example.umc10thtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Umc10thTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Umc10thTestApplication.class, args);
    }

}
