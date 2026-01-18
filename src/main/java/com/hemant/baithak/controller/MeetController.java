package com.hemant.baithak.controller;

import com.hemant.baithak.dto.MeetCreationRequest;
import com.hemant.baithak.dto.MeetCreationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meet")
public class MeetController {

  @PostMapping
  public ResponseEntity<MeetCreationResponse> createMeet(
      @RequestBody MeetCreationRequest meetCreationRequest
  ) {



  }

}
