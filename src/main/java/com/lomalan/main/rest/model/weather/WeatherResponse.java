package com.lomalan.main.rest.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse implements Serializable {

    @JsonProperty("coord")
    private Coordinates coordinates;

    private List<Weather> weather;

    @JsonProperty("main")
    private WeatherInfo weatherInfo;

    @JsonProperty("name")
    private String cityName;

    private String windSpeed;
    private String cloudiness;

    @JsonProperty("wind")
    public void windSpeedDeserializer(Map<String, Object> wind) {
        this.windSpeed = wind.get("speed").toString();
    }

    @JsonProperty("clouds")
    public void cloudinessDeserializer(Map<String, Object> clouds) {
        this.cloudiness = clouds.get("all").toString();
    }
}
