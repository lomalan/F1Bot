package com.lomalan.main.rest.client.f1;

import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.service.utils.RaceResultsParser;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class F1SchedulesClient {

    @Value("${ergast.api}")
    private String ergastApi;

    private static final String CURRENT_SEASON_NEXT_RACE_ENDPOINT = "current/next.json";

    public Race getNextRace() {
        String urlToExecute = ergastApi.concat(CURRENT_SEASON_NEXT_RACE_ENDPOINT);
        try {
            String response = F1ClientHelper.getJson(urlToExecute);
            log.info("F1Schedules response is: {}", response);
            return RaceResultsParser.parseToRace(response);
        } catch (IOException e) {
            log.error("F1Schedules resulted with error", e);
            throw new IllegalArgumentException("Next race should be present");
        }
    }
}
