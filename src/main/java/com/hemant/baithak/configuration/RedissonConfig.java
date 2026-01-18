package com.hemant.baithak.configuration;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;
import org.redisson.Redisson;

@Configuration
public class RedissonConfig {

  private final String redisConnectionString;

  public RedissonConfig(
      @Value("${redis.url}") String redisConnectionString
  ) {
    this.redisConnectionString = redisConnectionString;
  }

  @Bean
  public RedissonClient redissonClient() {

    Config config = new Config();

    config.useSingleServer().setAddress(redisConnectionString);

    return Redisson.create(config);
  }

}
