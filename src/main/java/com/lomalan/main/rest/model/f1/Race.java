package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
