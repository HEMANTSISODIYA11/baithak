package com.hemant.baithak.service.handler;

import com.hemant.baithak.dto.JoinMeetMessagePayload;
import com.hemant.baithak.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JoinMeetMessageHandler implements
    WebSocketMessageHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(WebSession webSession, Object payload) {
    JoinMeetMessagePayload messagePayload = objectMapper.convertValue(
        payload, new TypeReference<JoinMeetMessagePayload>() {}
    );


  }

  @Override
  public MessageType getMessageType() {
    return MessageType.JOIN;
  }
}
