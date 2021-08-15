package com.lomalan.main.dao.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class TelegramUser {

  @Id
  private String id;
  private Boolean isBot;
  private String firstName;
  private String lastName;
  private String userName;
  private String chatId;
}
