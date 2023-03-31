package com.lomalan.main.rest.f1;

import com.lomalan.main.TestConfig;
import com.lomalan.main.rest.client.f1.F1ResultsClient;
import com.lomalan.main.rest.model.f1.RaceResults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class, F1ResultsClient.class})
public class F1ResultsClientTest {
  @Autowired
  private F1ResultsClient testObject;
  @Test
  public void testRaceResults() throws IOException {
    RaceResults raceResults = testObject.getRaceResults();
    assertEquals("2023", raceResults.getSeason());
  }
  @Test
  public void testRaceResults1() throws IOException {
    RaceResults raceResults = testObject.getRaceResults();
    assertEquals(20, raceResults.getDriverStandings().size());
  }

}
