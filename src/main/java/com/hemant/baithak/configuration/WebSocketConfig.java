package com.hemant.baithak.configuration;

import com.hemant.baithak.service.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final ChatWebSocketHandler chatHandler;

  public WebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
    this.chatHandler = chatWebSocketHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(
        chatHandler, "/baithak/chat"
    ).setAllowedOrigins("*");
  }
}