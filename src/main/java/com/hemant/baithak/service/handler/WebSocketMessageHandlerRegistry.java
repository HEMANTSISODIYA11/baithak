package com.hemant.baithak.service.handler;

import com.hemant.baithak.enums.MessageType;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageHandlerRegistry {

  public Map<MessageType, WebSocketMessageHandler> webSocketMessageHandlers;

  public WebSocketMessageHandlerRegistry(
      final List<WebSocketMessageHandler> webSocketMessageHandlerList
  ) {

    webSocketMessageHandlers = ListUtils.emptyIfNull(
        webSocketMessageHandlerList
    ).stream().collect(
        Collectors.toMap(
            WebSocketMessageHandler::getMessageType,
          webSocketMessageHandler -> webSocketMessageHandler
        )
    );
  }

  public WebSocketMessageHandler getWebSocketMessageHandler(
      final MessageType messageType
  ) {

    if(webSocketMessageHandlers.containsKey(messageType)) {
      return webSocketMessageHandlers.get(messageType);
    }

    throw new EntityNotFoundException(
        String.format("handler not found for %s", messageType)
    );
  }

}
