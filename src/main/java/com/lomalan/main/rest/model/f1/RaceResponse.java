package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaceResponse {
  @JsonProperty("MRData")
  private MRData mrData;
}
