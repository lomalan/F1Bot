package com.lomalan.main.rest.client.weather;

import com.lomalan.main.rest.model.weather.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherClient {

  private final RestTemplate restTemplate;

  @Value("${weather.api}")
  private String weatherApi;
  @Value("${weather.api.key}")
  private String weatherApiKey;

  public WeatherClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public WeatherResponse getWeatherOnRaceLocation(String cityName) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApi)
        .queryParam("appId", weatherApiKey)
        .queryParam("q", cityName)
        .queryParam("units", "metric");
    HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
    ResponseEntity<WeatherResponse> weatherResponse =
        restTemplate.exchange(builder.toUriString()
            .replaceAll("%20", " "), HttpMethod.GET, entity, WeatherResponse.class);

    return weatherResponse.getBody();
  }

}
