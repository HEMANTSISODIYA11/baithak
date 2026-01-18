package com.hemant.baithak.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MeetCreationRequest {

  private String description;
  private String createdBy;

}
