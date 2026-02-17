package com.hemant.baithak.service.handler;

import com.hemant.baithak.client.RedisClient;
import com.hemant.baithak.client.RedisListener;
import com.hemant.baithak.constant.Constants;
import com.hemant.baithak.dto.JoinMeetMessagePayload;
import com.hemant.baithak.enums.MessageType;
import com.hemant.baithak.repository.SessionRepository;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class JoinMeetMessageHandler implements
    WebSocketMessageHandler {

  private final ObjectMapper objectMapper;
  private final RedisClient redisClient;
  private final RedisListener redisListener;
  private final SessionRepository sessionRepository;

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
    // sessions:meet:{meetId} -> set(session)
    // session:{session}:user -> name

//    MEETING_LIST_CACHE_KEY = "meeting";
//    SESSION_CACHE_KEY = "session:%s";
//    MEETING_TO_SESSIONS_CACHE_KEY = "sessions:meet:%s";
//    SESSION_TO_USER_NAME_CACHE_KEY = "session:%s:user";

//    public static final String SESSION_TO_MEET_ID_CACHE_KEY = "session:%s:meet";
//    public static final String MEET_TO_SUBSCRIPTION_STATUS_CACHE_KEY = "meet:%s";

    addListenerForMeet(meetId);

    redisClient.putObjectInSet(
        Constants.MEETING_LIST_CACHE_KEY,
        meetId
    );

    sessionRepository.addWebSession(
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

  private void addListenerForMeet(
      final String meetId
  ) {

    boolean entryExists = redisClient.hasObjectInSet(
        Constants.MEETING_LIST_CACHE_KEY,
        meetId
    );

    if(entryExists) {
      return;
    }

    try {
      RLock lock = redisClient.getLock(
          String.format(Constants.MEET_TO_SUBSCRIPTION_STATUS_CACHE_KEY, meetId)
      );

      if(lock.tryLock(100, 10000, TimeUnit.MILLISECONDS)) {

        try {

          


        } finally {
          lock.unlock();
        }
      }
    } catch (Exception exception) {
      log.error(
          "exception occurred while subscribing to the redis for meeting {}", meetId
      );
    }

    redisListener.subscribeToChannel(
        meetId
    );

  }

}
