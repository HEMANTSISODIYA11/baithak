package com.hemant.baithak.client;

import com.hemant.baithak.dto.RedisChatMessage;
import com.hemant.baithak.dto.TextMessagePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

  public final RedisClient redisClient;

  public void publish(
      final WebSocketSession webSocketSession,
      final TextMessagePayload textMessagePayload
  ) {

    redisClient.publishInTopic(
        textMessagePayload.getMeetId(),
        RedisChatMessage.builder()
            .webSocketSessionId(webSocketSession.getId())
            .message(textMessagePayload)
            .build()
    );

  }

}
