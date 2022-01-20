package com.lomalan.main.dao.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
public class Suggestion {
  @Id
  private String id;
  private String chatId;
  @Indexed(unique = true)
  private String suggestionText;
  private LocalDate dateStamp;
}
