package com.hemant.baithak.client;

import com.hemant.baithak.dto.RedisChatMessage;
import com.hemant.baithak.service.RedisMessageListener;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisListener {

  private final RedisMessageListener redisMessageListener;
  private final RedissonClient redissonClient;


  public void subscribeToChannel(
      final String channelId
  ) {

    RTopic rTopic = redissonClient.getTopic(
        channelId
    );

    rTopic.addListener(
        RedisChatMessage.class, redisMessageListener
    );
  }

}
