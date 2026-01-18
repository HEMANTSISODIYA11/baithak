package com.hemant.baithak.controller;

import com.hemant.baithak.dto.ChatMessage;
import java.security.Principal;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;

  public ChatController(
      final SimpMessagingTemplate messagingTemplate
  ) {
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping("rooms/{roomId}/chat")
  public void chat(
      @DestinationVariable String roomId,
      ChatMessage message,
      Principal principal
  ) {

    message.setTimeStamp(System.currentTimeMillis());
    message.setSender(
        principal.getName()
    );

    messagingTemplate.convertAndSend(
        "/topic/rooms/" + roomId, message
    );

  }


}
