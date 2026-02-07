package com.hemant.baithak.service;

import com.hemant.baithak.dto.RedisChatMessage;
import com.hemant.baithak.service.handler.RedisChatMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisListener {

  public final RedisChatMessageHandler redisChatMessageHandler;

  public void listen(
      final RedisChatMessage chatMessage
  ) {

    redisChatMessageHandler.listenMessage(
        chatMessage.webSocketSessionId,
        chatMessage.getMessage()
    );
  }

}
