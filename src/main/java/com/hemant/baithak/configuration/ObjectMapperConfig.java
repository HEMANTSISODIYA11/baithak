package com.hemant.baithak.configuration;


import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;

public class ObjectMapperConfig {

  @Bean
  public ObjectMapper create() {
    return new ObjectMapper();
  }

}
