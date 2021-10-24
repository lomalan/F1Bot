package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
  @JsonProperty("lat")
  private String latitude;
  @JsonProperty("long")
  private String longitude;
  private String locality;
  private String country;
}
