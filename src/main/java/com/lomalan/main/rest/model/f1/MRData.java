package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MRData {
  @JsonProperty("RaceTable")
  private RaceTable raceTable;
}
