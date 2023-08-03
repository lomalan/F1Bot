package com.lomalan.main.rest.client.livetiming;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class LiveTimingClient {

  private final RestTemplate restTemplate;

  private final String liveTimingApi;
  private static final String RACE_ENDPOINT = "/data";

  public LiveTimingClient(
      RestTemplate restTemplate, @Value("${live.timing.api}") String liveTimingApi) {
    this.restTemplate = restTemplate;
    this.liveTimingApi = liveTimingApi;
  }

  public Optional<String> getRaceLiveTiming() {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(liveTimingApi.concat(RACE_ENDPOINT));
    HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
    ResponseEntity<String> response =
        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
    log.info("LifeTiming response is: {}", response);
    if (!response.getStatusCode().equals(HttpStatus.OK)) {
      return Optional.empty();
    }
    return Optional.ofNullable(response.getBody());
  }
}
