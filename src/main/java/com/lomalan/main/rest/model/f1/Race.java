package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Race {
  private String season;
  private String round;
  private String url;
  private String raceName;
  private String date;
  private String time;
  @JsonProperty("Circuit")
  private Circuit circuit;
}
