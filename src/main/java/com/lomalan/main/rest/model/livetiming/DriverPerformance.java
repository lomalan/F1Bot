package com.lomalan.main.rest.model.livetiming;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class DriverPerformance {
  @JsonProperty("F")
  private List<String> driverInfo;
}
