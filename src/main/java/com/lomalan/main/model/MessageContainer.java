package com.lomalan.main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Builder
@AllArgsConstructor
@Getter
@Data
public class MessageContainer {
  private String text;
  private InputFile photo;

  public MessageContainer(String text) {
    this.text = text;
  }
}
