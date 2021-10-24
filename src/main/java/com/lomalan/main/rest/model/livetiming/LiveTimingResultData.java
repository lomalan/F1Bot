package com.lomalan.main.rest.model.livetiming;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class LiveTimingResultData {
  @JsonProperty("L")
  private String currentLap;
  @JsonProperty("TL")
  private String totalLaps;
  @JsonProperty("S")
  private String session;
  @JsonProperty("ST")
  private String sessionType;
  @JsonProperty("R")
  private String raceTitle;
  @JsonProperty("DR")
  private List<DriverPerformance> driverPerformance;
}
