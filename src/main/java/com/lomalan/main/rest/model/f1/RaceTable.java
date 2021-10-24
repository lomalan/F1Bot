package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class RaceTable {
  private String season;
  private String round;
  @JsonProperty("Races")
  private List<Race> races;
}
