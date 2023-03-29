package com.lomalan.main.rest.client.f1;

import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.f1.RaceResponse;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class F1SchedulesClient {

  private final RestTemplate restTemplate;

  @Value("${ergast.api}")
  private String ergastApi;

  private static final String CURRENT_SEASON_NEXT_RACE_ENDPOINT = "current/next.json";


  public F1SchedulesClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Race getNextRace() {
    String urlToExecute = getUrlToExecute(CURRENT_SEASON_NEXT_RACE_ENDPOINT);

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlToExecute);

    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<RaceResponse> nextRace = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
        RaceResponse.class);
    log.info("F1Schedules response is: {}", nextRace);
    if (Objects.nonNull(nextRace.getBody()) && Objects.nonNull(nextRace.getBody().getMrData())
        && Objects.nonNull(nextRace.getBody().getMrData().getRaceTable())
        && Objects.nonNull(nextRace.getBody().getMrData().getRaceTable().getRaces())) {
      return nextRace.getBody().getMrData().getRaceTable().getRaces().stream()
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Next race should be present!"));
    }
    throw new IllegalArgumentException("Next race should be present");
  }

  private String getUrlToExecute(String endpointToExecute) {
    return ergastApi.concat(endpointToExecute);
  }
}
