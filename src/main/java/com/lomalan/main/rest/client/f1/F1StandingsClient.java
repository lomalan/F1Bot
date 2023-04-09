package com.lomalan.main.rest.client.f1;

import static com.lomalan.main.rest.client.f1.F1ClientHelper.getJson;
import com.lomalan.main.rest.model.f1.DriverStandings;
import com.lomalan.main.service.utils.DriverStandingsParser;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class F1StandingsClient {

 private final String ergastApi;

  public F1StandingsClient(@Value(value = "${ergast.api}") String ergastApi) {
    this.ergastApi = ergastApi;
  }

  private static final String CURRENT_SEASON_NEXT_RACE_ENDPOINT = "current/driverStandings.json";

  public List<DriverStandings> getDriverStandings() throws IOException {
    String urlToExecute = ergastApi.concat(CURRENT_SEASON_NEXT_RACE_ENDPOINT);
    String response = getJson(urlToExecute);
    log.info("F1Standings response is: {}", response);
    return DriverStandingsParser.parseToDriverStandings(response);
  }
}
