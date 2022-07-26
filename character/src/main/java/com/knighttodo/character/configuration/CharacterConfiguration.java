package com.knighttodo.character.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@PropertySource(value = "classpath:application-character.yml")
public class CharacterConfiguration {

}
