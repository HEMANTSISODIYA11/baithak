package com.hemant.baithak.service.handler;

import com.hemant.baithak.client.RedisClient;
import com.hemant.baithak.constant.Constants;
import com.hemant.baithak.dto.LeaveMeetPayload;
import com.hemant.baithak.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class LeaveMeetMessageHandler implements WebSocketMessageHandler{

  private final ObjectMapper objectMapper;
  private final RedisClient redisClient;

  @Override
  public void handle(WebSocketSession webSession, Object payload) {

    LeaveMeetPayload messagePayload = objectMapper.convertValue(
        payload, new TypeReference<LeaveMeetPayload>() {
        }
    );

    String sessionId = webSession.getId();
    String meetId = messagePayload.getMeetId();

    // meets -> set(meetId)
    // session:{sessionId} -> websession, TTL
    // sessions:meet:{meetId} -> set(session)
    // session:{session}:user -> name

    redisClient.removeKey(
        String.format(Constants.SESSION_CACHE_KEY, sessionId)
    );

    redisClient.removeObjectFromSet(
        String.format(Constants.MEETING_TO_SESSIONS_CACHE_KEY, meetId),
        sessionId
    );

    redisClient.removeKey(
        String.format(Constants.SESSION_TO_USER_NAME_CACHE_KEY, sessionId)
    );

    if(!redisClient.isSetExists(
        String.format(Constants.MEETING_TO_SESSIONS_CACHE_KEY, meetId)
    )) {
      redisClient.removeObjectFromSet(
          Constants.MEETING_LIST_CACHE_KEY,
          meetId
      );
    }
  }

  @Override
  public MessageType getMessageType() {
    return null;
  }
}
