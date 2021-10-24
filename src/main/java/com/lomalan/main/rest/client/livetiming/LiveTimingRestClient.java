package com.lomalan.main.rest.client.livetiming;

import com.lomalan.main.rest.model.livetiming.SessionInfo;
import com.lomalan.main.rest.model.livetiming.StreamingStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class LiveTimingRestClient {

  private final RestTemplate restTemplate;

  @Value("${live.timing.api}")
  private String liveTimingApi;

  private static final String SESSION_INFO = "SessionInfo.json";
  private static final String STREAMING_STATUS = "StreamingStatus.json";

  public LiveTimingRestClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private SessionInfo getCurrentSessionInfo() {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(liveTimingApi.concat(SESSION_INFO));
    HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, SessionInfo.class).getBody();
  }


  public StreamingStatus getStreamingStatus() {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(liveTimingApi.concat(STREAMING_STATUS));
    HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, StreamingStatus.class).getBody();
  }
}
