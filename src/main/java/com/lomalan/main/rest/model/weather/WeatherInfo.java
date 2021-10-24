package com.lomalan.main.rest.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class WeatherInfo implements Serializable {
  private int temp;
  @JsonProperty("feels_like")
  private int feelTemp;
  @JsonProperty("temp_min")
  private int minTemp;
  @JsonProperty("temp_max")
  private int maxTemp;
  private int pressure;
  private int humidity;

}
