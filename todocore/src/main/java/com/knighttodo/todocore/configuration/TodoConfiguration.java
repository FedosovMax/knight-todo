package com.knighttodo.todocore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.yml")
public class TodoConfiguration {

}
