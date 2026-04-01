package com.hemant.baithak.service.handler;

import com.hemant.baithak.dto.PingMessagePayload;
import com.hemant.baithak.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class PingMessageHandler implements WebSocketMessageHandler {

  private final ObjectMapper objectMapper;

  @Override
  @SneakyThrows
  public void handle(WebSocketSession webSession, Object payload) {

    PingMessagePayload pingMessagePayload = objectMapper.convertValue(
        payload, new TypeReference<PingMessagePayload>() {
        }
    );

    webSession.sendMessage(
        new TextMessage("PONG")
    );
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.PING;
  }
}
