package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Circuit {
  private String circuitId;
  private String url;
  private String circuitName;
  @JsonProperty("Location")
  private Location location;
}
