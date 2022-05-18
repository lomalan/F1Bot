package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Race extends Event {
  private String season;
  private String round;
  private String url;
  private String raceName;
  @JsonProperty("Circuit")
  private Circuit circuit;
  @JsonProperty("FirstPractice")
  private Event firstPractice;
  @JsonProperty("SecondPractice")
  private Event secondPractice;
  @JsonProperty("ThirdPractice")
  private Event thirdPractice;
  @JsonProperty("Qualifying")
  private Event quali;
  @JsonProperty("Sprint")
  private Event sprint;
}
