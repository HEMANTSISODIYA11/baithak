package com.hemant.baithak.helper;

import com.hemant.baithak.client.RedisClient;
import com.hemant.baithak.constant.Constants;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingCacheHelper {

  // meets -> set(meetId)
  // session:{sessionId} -> websession, TTL
  // sessions:meet:{meetId} -> set(session)
  // session:{session}:user -> name

  private final RedisClient redisClient;

  public void putMeeting(String meetId) {
    redisClient.putObjectInSet(
        Constants.MEETING_LIST_CACHE_KEY, meetId
    );
  }

  public Set<String> getMeetings() {
    Set<Object> meetings = redisClient.getSetFromCache(Constants.MEETING_LIST_CACHE_KEY);
    return SetUtils.emptyIfNull(
        meetings
    ).stream().map(meeting -> (String) meeting).collect(Collectors.toSet());
  }




}
