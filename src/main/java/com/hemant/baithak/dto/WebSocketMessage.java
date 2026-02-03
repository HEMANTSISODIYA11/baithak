package com.hemant.baithak.dto;

import com.hemant.baithak.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage<T> {

  private MessageType messageType;
  T payload;
}
