package com.lomalan.main.rest.client.f1;

import com.lomalan.main.rest.model.f1.DriverStandings;
import com.lomalan.main.service.utils.DriverStandingsParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@AllArgsConstructor
public class F1StandingsClient {

  private static final String CURRENT_SEASON_NEXT_RACE_ENDPOINT = "current/driverStandings.json";

  public List<DriverStandings> getDriverStandings() throws IOException {
    String urlToExecute = getUrlToExecute(CURRENT_SEASON_NEXT_RACE_ENDPOINT);
    return DriverStandingsParser.parseToDriverStandings(getJson(urlToExecute));
  }

  private String getUrlToExecute(String endpointToExecute) {
    return "https://ergast.com/api/f1/".concat(endpointToExecute);
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
    return response.toString();
  }
}
