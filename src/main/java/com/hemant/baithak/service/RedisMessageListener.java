package com.hemant.baithak.service;

import com.hemant.baithak.dto.RedisChatMessage;
import com.hemant.baithak.service.handler.RedisChatMessageHandler;
import lombok.RequiredArgsConstructor;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessageListener implements
    MessageListener<RedisChatMessage> {

  private final RedisChatMessageHandler redisChatMessageHandler;

  @Override
  public void onMessage(CharSequence channel, RedisChatMessage chatMessage) {

    redisChatMessageHandler.listenMessage(
        chatMessage.webSocketSessionId,
        chatMessage.getMessage()
    );
  }
}
