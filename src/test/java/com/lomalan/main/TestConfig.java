package com.lomalan.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class TestConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
