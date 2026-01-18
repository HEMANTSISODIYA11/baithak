package com.hemant.baithak.configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Component
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements
    WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(
      final StompEndpointRegistry stompEndpointRegistry
  ) {

    stompEndpointRegistry.addEndpoint(
        "/ws"
    ).setAllowedOrigins("*");
  }

  @Override
  public void configureMessageBroker(
      final MessageBrokerRegistry messageBrokerRegistry
  ) {
    messageBrokerRegistry.setApplicationDestinationPrefixes(
        "/app"
    );

    messageBrokerRegistry.enableSimpleBroker(
        "/topic",
        "/queue"
    );
  }




}
