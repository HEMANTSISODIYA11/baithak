package com.hemant.baithak.service.handler;

import com.hemant.baithak.enums.MessageType;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketMessageHandler {

  void handle(
      WebSocketSession webSession,
      Object payload
  );

  MessageType getMessageType();
}
