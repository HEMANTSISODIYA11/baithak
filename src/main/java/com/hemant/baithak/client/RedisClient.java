package com.hemant.baithak.client;

import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisClient {

  private final RedissonClient redissonClient;

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

  public RLock getLock(
      final String key
  ) throws InterruptedException {
    RLock lock = redissonClient.getLock(key);
    return lock;
  }

}
