package com.lomalan.main.rest.client.f1;

import com.lomalan.main.rest.model.f1.RaceResults;
import com.lomalan.main.service.utils.RaceResultsParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
public class F1ResultsClient {

  private final String ergastApi;

  private static final String CURRENT_LAST_RACE_RESULTS_ENDPOINT = "current/last/results.json";

  public F1ResultsClient(@Value(value = "${ergast.api}") String ergastApi) {
    this.ergastApi = ergastApi;
  }

  public RaceResults getRaceResults() throws IOException {
    String response = F1ClientHelper.getJson(ergastApi.concat(CURRENT_LAST_RACE_RESULTS_ENDPOINT));
    log.info("F1Results response is: {}", response);
    return RaceResultsParser.parseToRaceResults(response);
  }
}
