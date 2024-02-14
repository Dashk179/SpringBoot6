package com.curso.bestTravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:configs/api_currently.properties")
public class PropertiesConfig {
}
