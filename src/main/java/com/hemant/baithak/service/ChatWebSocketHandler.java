package com.hemant.baithak.service;

import com.hemant.baithak.dto.WebSocketMessage;
import com.hemant.baithak.service.handler.WebSocketMessageHandlerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  private final WebSocketMessageHandlerRegistry webSocketMessageHandlerRegistry;

  @Override
  public void afterConnectionEstablished(
      WebSocketSession session
  ) throws Exception{
      log.info("A new websocket connection has been established {}", session.getId());
  }

  @Override
  protected void handleTextMessage(
      WebSocketSession session,
      TextMessage message
  ) {

    WebSocketMessage<Object> textMessage = objectMapper.convertValue(
        message.getPayload(),
        new TypeReference<WebSocketMessage<Object>>() {
        }
    );

    webSocketMessageHandlerRegistry.getWebSocketMessageHandler(
        textMessage.getMessageType()
    ).handle(
        session,
        textMessage.getPayload()
    );

    log.info(
        "message received {}", message.getPayload()
    );
  }

  @Override
  public void afterConnectionClosed(
      WebSocketSession webSocketSession,
      CloseStatus status
  ) {

  }

}
