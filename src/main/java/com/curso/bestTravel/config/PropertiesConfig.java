package com.curso.bestTravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:configs/api_currently.properties"),
        @PropertySource(value = "classpath:configs/redis.properties")

})
public class PropertiesConfig {
}
