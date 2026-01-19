package com.hemant.baithak.service.handler;

import com.hemant.baithak.enums.MessageType;
import org.springframework.web.server.WebSession;

public interface WebSocketMessageHandler {

  void handle(
      WebSession webSession,
      Object payload
  );

  MessageType getMessageType();
}
