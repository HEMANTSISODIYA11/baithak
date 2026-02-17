package com.hemant.baithak.service.handler;

import com.hemant.baithak.client.RedisClient;
import com.hemant.baithak.constant.Constants;
import com.hemant.baithak.dto.TextMessagePayload;
import com.hemant.baithak.dto.TextMessageResponse;
import com.hemant.baithak.repository.SessionRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class RedisChatMessageHandler {

  private final RedisClient redisClient;
  private final ObjectMapper objectMapper;
  private final SessionRepository sessionRepository;

  @SneakyThrows
  public void listenMessage(
      final String webSocketSessionId,
      final Object payload
  ) {

    TextMessagePayload textMessagePayload = objectMapper.convertValue(
        payload, new TypeReference<TextMessagePayload>() {
        }
    );

    Set<Object> sessionIds = SetUtils.emptyIfNull(
        redisClient.getObjectsFromSet(
            String.format(Constants.MEETING_TO_SESSIONS_CACHE_KEY, textMessagePayload.getMeetId())
        )
    );

    Set<WebSocketSession> webSocketSessions = SetUtils.emptyIfNull(sessionIds).stream().map(
        sessionId -> sessionRepository.getWebSession((String) sessionId)
    ).filter(Optional::isPresent).map(
        Optional::get
    ).collect(Collectors.toSet());

    for(WebSocketSession webSocketSessionFromMeet : webSocketSessions) {
      if(!webSocketSessionId.equals(webSocketSessionFromMeet.getId())) {

        if(!webSocketSessionFromMeet.isOpen()) {
          continue;
        }

        String user = redisClient.getObjectInKey(
            String.format(Constants.SESSION_TO_USER_NAME_CACHE_KEY, webSocketSessionFromMeet.getId())
        ).get().toString();

        webSocketSessionFromMeet.sendMessage(
            new TextMessage(
                objectMapper.writeValueAsString(
                    TextMessageResponse.builder()
                        .message(textMessagePayload.getMessage())
                        .sender(user)
                        .build()
                )
            )
        );
      }
    }
  }

}


// i will have the locally unique listener ids
