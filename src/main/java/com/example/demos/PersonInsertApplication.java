package com.example.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PersonInsertApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonInsertApplication.class, args);
    }

}
