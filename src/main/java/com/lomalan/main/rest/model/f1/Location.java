package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {
  @JsonProperty("lat")
  private String latitude;
  @JsonProperty("long")
  private String longitude;
  private String locality;
  private String country;
}
