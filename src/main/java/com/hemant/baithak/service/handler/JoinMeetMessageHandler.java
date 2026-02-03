package com.hemant.baithak.service.handler;

import com.hemant.baithak.client.RedisClient;
import com.hemant.baithak.constant.Constants;
import com.hemant.baithak.dto.JoinMeetMessagePayload;
import com.hemant.baithak.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JoinMeetMessageHandler implements
    WebSocketMessageHandler {

  private final ObjectMapper objectMapper;
  private final RedisClient redisClient;

  @Override
  public void handle(WebSocketSession webSession, Object payload) {
    JoinMeetMessagePayload messagePayload = objectMapper.convertValue(
        payload, new TypeReference<JoinMeetMessagePayload>() {
        }
    );

    String sessionId = webSession.getId();
    String meetId = messagePayload.getMeetId();
    String member = messagePayload.getName();

    // meets -> set(meetId)
    // session:{sessionId} -> websession, TTL
    // sessions:meet:{meetId} -> set(session)
    // session:{session}:user -> name

//    MEETING_LIST_CACHE_KEY = "meeting";
//    SESSION_CACHE_KEY = "session:%s";
//    MEETING_TO_SESSIONS_CACHE_KEY = "sessions:meet:%s";
//    SESSION_TO_USER_NAME_CACHE_KEY = "session:%s:user";

    redisClient.putObjectInSet(
        Constants.MEETING_LIST_CACHE_KEY,
        meetId
    );

    redisClient.putObjectInKey(
        String.format(Constants.SESSION_CACHE_KEY, sessionId),
        webSession
    );

    redisClient.putObjectInSet(
        String.format(Constants.MEETING_TO_SESSIONS_CACHE_KEY, meetId),
        sessionId
    );

    redisClient.putObjectInKey(
        String.format(Constants.SESSION_TO_USER_NAME_CACHE_KEY, sessionId), member
    );


  }

  @Override
  public MessageType getMessageType() {
    return MessageType.JOIN;
  }
}
