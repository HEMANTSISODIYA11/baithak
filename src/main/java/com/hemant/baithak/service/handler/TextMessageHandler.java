package com.hemant.baithak.service.handler;


import com.hemant.baithak.dto.TextMessagePayload;
import com.hemant.baithak.enums.MessageType;
import com.hemant.baithak.client.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class TextMessageHandler implements WebSocketMessageHandler {

  private final ObjectMapper objectMapper;
  private final RedisPublisher redisPublisher;

  @Override
  @SneakyThrows
  public void handle(WebSocketSession webSocketSession, Object payload) {

    // meets -> set(meetId)
    // session:{sessionId} -> websession, TTL
    // sessions:meet:{meetId} -> set(session)
    // session:{session}:user -> name

    TextMessagePayload textMessagePayload = objectMapper.convertValue(
        payload, new TypeReference<TextMessagePayload>() {
        }
    );

    redisPublisher.publish(
        webSocketSession,
        textMessagePayload
    );
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.CHAT;
  }
}
