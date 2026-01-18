package com.hemant.baithak.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatMessage {

  private String message;
  private Long timeStamp;
  private String sender;
}
