package com.hemant.baithak.service;

import com.hemant.baithak.dto.MeetCreationRequest;
import com.hemant.baithak.dto.MeetCreationResponse;
import com.hemant.baithak.mapper.MeetMapper;
import com.hemant.baithak.repository.MeetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetService {

  private final MeetRepository meetRepository;

  public MeetCreationResponse createMeet(
      final MeetCreationRequest meetCreationRequest
  ) {
    return MeetMapper.meetCreationResponse(
        meetRepository.save(
            MeetMapper.mapMeetCreationRequestToMeet(
                meetCreationRequest
            )
        )
    );
  }

}
