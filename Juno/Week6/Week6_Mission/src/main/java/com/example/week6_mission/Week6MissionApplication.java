package com.example.week6_mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week6MissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week6MissionApplication.class, args);
    }

}
