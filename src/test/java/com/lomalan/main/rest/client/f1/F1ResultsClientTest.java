package com.lomalan.main.rest.client.f1;

import com.lomalan.main.TestConfig;
import com.lomalan.main.rest.model.f1.RaceResults;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    assertTrue(2023 >= Integer.parseInt(raceResults.getSeason()));
  }

  @Test
  public void testDriversQuantity() throws IOException {
    RaceResults raceResults = testObject.getRaceResults();
    assertEquals(20, raceResults.getDriverStandings().size());
  }
}
