package com.hemant.baithak.client;

import static java.time.temporal.ChronoUnit.HOURS;

import com.hemant.baithak.dto.RedisChatMessage;
import com.hemant.baithak.service.RedisListener;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisClient {

  private final RedissonClient redissonClient;
  private final RedisListener redisListener;

  public void putObjectInKey(
      final String key, final Object object
  ) {
    RBucket<Object> redissonBucket = redissonClient.getBucket(key);
    redissonBucket.set(object);
  }

  public void putObjectInKey(
      final String key, final Object object, long ttl
  ) {
    RBucket<Object> redissonBucket = redissonClient.getBucket(key);
    redissonBucket.set(object);
    redissonBucket.expire(Instant.now().plus(ttl, HOURS));
  }

  public void putObjectInSet(
      final String key, final Object object
  ) {
    RSet<Object> redissonSet = redissonClient.getSet(key);
    redissonSet.add(object);
  }

  public Optional<Object> getObjectInKey(
      final String key
  ) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    if(Objects.nonNull(bucket)) {
      return Optional.ofNullable(bucket.get());
    }

    return Optional.empty();
  }

  public Set<Object> getSetFromCache(String key) {
    RSet<Object> redissonSet = redissonClient.getSet(key);
    return redissonSet.readAll();
  }

  public void removeKey(final String key) {
    RBucket<Object> redissonBucket = redissonClient.getBucket(key);
    redissonBucket.delete();
  }

  public void removeObjectFromSet(final String key, final Object object) {
    RSet<Object> redissonSet = redissonClient.getSet(key);
    redissonSet.remove(object);

    if(redissonSet.isEmpty()) {
      redissonSet.delete();
    }
  }

  public boolean isSetExists(
      final String key
  ) {
    RSet<Object> redissonSet = redissonClient.getSet(key);
    return redissonSet.isExists();
  }

  public Set<Object> getObjectsFromSet(
      final String key
  ) {

    RSet<Object> redissonSet = redissonClient.getSet(key);

    return redissonSet.readAll();
  }

  public boolean hasObjectInSet(
      final String key, final Object setObject
  ) {

    RSet<Object> redissonSet = redissonClient.getSet(key);
    return redissonSet.readAll().contains(setObject);
  }

  public void publishInTopic(
      final String channelId,
      final Object payload
  ) {

    RTopic rTopic = redissonClient.getTopic(channelId);
    rTopic.publish(
        payload
    );
  }

  public void subscribeToChannel(
      final String channelId
  ) {

    RTopic rTopic = redissonClient.getTopic(
        channelId
    );

    rTopic.addListener(
        RedisChatMessage.class, new MessageListener<RedisChatMessage>() {


          @Override
          public void onMessage(CharSequence channel, RedisChatMessage chatMessage) {
            redisListener.listen(
                chatMessage
            );
          }
        }
    );
  }

}
