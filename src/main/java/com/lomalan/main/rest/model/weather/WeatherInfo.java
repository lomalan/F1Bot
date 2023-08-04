package com.lomalan.main.rest.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
