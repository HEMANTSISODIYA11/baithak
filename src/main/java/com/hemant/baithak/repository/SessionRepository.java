package com.hemant.baithak.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SessionRepository {

  private final ConcurrentHashMap<String, WebSocketSession> webSocketSessionConcurrentHashMap = new ConcurrentHashMap<>();

  public void addWebSession(
      final WebSocketSession webSocketSession
  ) {

    webSocketSessionConcurrentHashMap.put(
        webSocketSession.getId(),
        webSocketSession
    );
  }

  public boolean hasSession(
      final String webSocketSessionId
  ) {

    return webSocketSessionConcurrentHashMap.containsKey(
        webSocketSessionId
    );
  }

  public void removeSession(
      final String webSocketSessionId
  ) {

    webSocketSessionConcurrentHashMap.remove(webSocketSessionId);
  }

  public Optional<WebSocketSession> getWebSession(
      final String sessionId
  ) {

    return Optional.ofNullable(
        webSocketSessionConcurrentHashMap.get(sessionId)
    );
  }


}
