package com.hemant.baithak.client;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisClient {

  private final RedissonClient redissonClient;


}
