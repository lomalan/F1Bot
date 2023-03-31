package com.lomalan.main.rest.client.f1;

import com.lomalan.main.rest.model.f1.RaceResults;
import com.lomalan.main.service.utils.RaceResultsParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class F1ResultsClient {

  private String ergastApi;

  private static final String CURRENT_LAST_RACE_RESULTS_ENDPOINT = "current/last/results.json";

  public F1ResultsClient(@Value(value = "${ergast.api}") String ergastApi) {
    this.ergastApi = ergastApi;
  }
  private String getUrlToExecute(String endpointToExecute) {
    return ergastApi.concat(endpointToExecute);
  }
  public RaceResults getRaceResults() throws IOException {
    return RaceResultsParser.parseToRaceResults(getJson(getUrlToExecute(CURRENT_LAST_RACE_RESULTS_ENDPOINT)));
  }
  private String getJson(String url) throws IOException {
    URL obj = new URL(url);
    HttpURLConnection httpURLConnection = (HttpURLConnection)obj.openConnection();
    httpURLConnection.setRequestMethod(RequestMethod.GET.name());
    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
    StringBuilder response = new StringBuilder();

    String inputLine;
    while((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    log.info("F1Results response is: {}", response);
    return response.toString();
  }

}
