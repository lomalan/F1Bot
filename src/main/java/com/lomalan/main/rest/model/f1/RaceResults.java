package com.lomalan.main.rest.model.f1;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

@Data
public class RaceResults {
  private String season;
  private String round;
  private String url;
  private String raceName;
  @SerializedName("Results")
  private List<DriverStandings> driverStandings;

}
