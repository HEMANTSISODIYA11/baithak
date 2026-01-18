package com.hemant.baithak.mapper;

import com.hemant.baithak.dto.MeetCreationRequest;
import com.hemant.baithak.dto.MeetCreationResponse;
import com.hemant.baithak.model.Meet;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MeetMapper {

  public Meet mapMeetCreationRequestToMeet(
      final MeetCreationRequest meetCreationRequest
  ) {

    return Meet.builder()
        .description(
            meetCreationRequest.getDescription()
        )
        .createdBy(
            meetCreationRequest.getCreatedBy()
        )
        .meetId(
            String.format("BAITHAK-%s", UUID.randomUUID())
        )
        .build();
  }

  public MeetCreationResponse meetCreationResponse(
      final Meet meet
  ) {

    return MeetCreationResponse.builder()
        .meetId(meet.getMeetId())
        .createdBy(meet.getCreatedBy())
        .description(meet.getDescription())
        .build();
  }

}