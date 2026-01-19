package com.hemant.baithak.controller;

import com.hemant.baithak.dto.MeetCreationRequest;
import com.hemant.baithak.dto.MeetCreationResponse;
import com.hemant.baithak.service.MeetService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetController {

  private final MeetService meetService;

  @PostMapping
  public ResponseEntity<@NonNull MeetCreationResponse> createMeet(
      @RequestBody MeetCreationRequest meetCreationRequest
  ) {

    return ResponseEntity.ok(
      meetService.createMeet(
          meetCreationRequest
      )
    );
  }
}
