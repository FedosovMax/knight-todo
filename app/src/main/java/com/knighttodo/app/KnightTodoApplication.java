package com.knighttodo.app;

import com.knighttodo.app.config.KnightTodoAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = "com.knighttodo")
@Import(KnightTodoAppConfiguration.class)
public class KnightTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnightTodoApplication.class, args);
    }
}
