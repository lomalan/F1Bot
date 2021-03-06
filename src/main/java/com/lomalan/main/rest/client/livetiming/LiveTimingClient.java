package com.lomalan.main.rest.client.livetiming;

import java.util.Optional;
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
public class LiveTimingClient {

  private final RestTemplate restTemplate;

  @Value("${live.timing.api}")
  private String liveTimingApi;
  private static final String RACE_ENDPOINT = "/data";

  public LiveTimingClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Optional<String> getRaceLiveTiming() {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(liveTimingApi.concat(RACE_ENDPOINT));
    HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
    ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
      return Optional.empty();
    }
    return Optional.ofNullable(response.getBody());
  }
}
