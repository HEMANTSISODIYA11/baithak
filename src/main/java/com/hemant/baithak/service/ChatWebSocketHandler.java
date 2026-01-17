package com.hemant.baithak.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

  private final Map<String, String> sessionToRoomInfo = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(
      WebSocketSession session
  ) throws Exception{
      // do nothing
  }

  @Override
  protected void handleTextMessage(
      WebSocketSession session,
      TextMessage message
  ) {

    log.info(
        "message received {}", message.getPayload()
    );
  }

  private void handleJoin() {

  }

  @Override
  public void afterConnectionClosed(
      WebSocketSession webSocketSession,
      CloseStatus status
  ) {

  }





}
