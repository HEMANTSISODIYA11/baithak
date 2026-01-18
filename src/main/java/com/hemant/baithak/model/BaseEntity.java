package com.hemant.baithak.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long pk;

  @Default
  private Timestamp createdAt = Timestamp.from(Instant.now());

  @Default
  private Timestamp updatedAt = Timestamp.from(Instant.now());

  @Default
  private Boolean isActive = Boolean.TRUE;
}
