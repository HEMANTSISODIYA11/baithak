package com.hemant.baithak.model;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "meet")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(name = "meet_primary_key_seq")
public class Meet extends BaseEntity {

  private String meetId;

  private String description;

  private String createdBy;
}