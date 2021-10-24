package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Circuit {
  private String circuitId;
  private String url;
  private String circuitName;
  @JsonProperty("Location")
  private Location location;
}
