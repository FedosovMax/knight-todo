package com.knighttodo.character;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.knighttodo")
public class KnightCharacterApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnightCharacterApplication.class, args);
    }
}
