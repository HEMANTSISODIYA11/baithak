package com.hemant.baithak.service;

import com.hemant.baithak.dto.RedisChatMessage;
import com.hemant.baithak.service.handler.TextMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSubscriber {

  public final TextMessageHandler textMessageHandler;

  public void listen(
      final String channelId,
      final RedisChatMessage chatMessage
  ) {

    t


  }

}
