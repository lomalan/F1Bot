package com.lomalan.main.service.impl;

import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.rest.client.f1.F1ResultsClient;
import com.lomalan.main.rest.model.f1.Driver;
import com.lomalan.main.rest.model.f1.DriverStandings;
import com.lomalan.main.rest.model.f1.RaceResults;
import com.lomalan.main.service.last.impl.RaceResultsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RaceResultServiceImplTest {

  private final F1ResultsClient f1ResultsClient = mock(F1ResultsClient.class);

  private final RaceResultsServiceImpl testObject = new RaceResultsServiceImpl(f1ResultsClient);

  @Test
  public void whenClientThrowingErrorReturnsEmpty() throws IOException {
    Mockito.when(f1ResultsClient.getRaceResults()).thenThrow(IOException.class);
    Optional<MessageContainer> raceResults = testObject.getRaceResults();
    assertTrue(raceResults.isEmpty());
  }

  @Test
  public void whenClientNotThrowingErrorReturnMessage() throws IOException {
    Mockito.when(f1ResultsClient.getRaceResults()).thenReturn(createRaceResults());
    Optional<MessageContainer> raceResults = testObject.getRaceResults();
    assertTrue(raceResults.isPresent());
    assertNull(raceResults.get().getPhoto());
    String expectedResult = "\uD83C\uDFCERace\uD83C\uDFCE\n" +
      "\n" +
      "Season: Season\n" +
      "\n" +
      "Round: Round\n" +
      "\n" +
      "Driver standings: \n" +
      "\n" +
      "\uD83C\uDFC1Name Points\n" +
      "\n" +
      "Position. Driver code Points\n" +
      "\n" +
      "Wiki info: null";

    assertEquals(expectedResult, raceResults.get().getText());
  }

  private RaceResults createRaceResults() {
    RaceResults raceResults = new RaceResults();
    raceResults.setRaceName("Race");
    raceResults.setSeason("Season");
    raceResults.setRound("Round");
    DriverStandings driverStandings = new DriverStandings();
    Driver driver = new Driver();
    driver.setCode("Driver code");
    driverStandings.setDriver(driver);
    driverStandings.setPosition("Position");
    driverStandings.setPoints("Points");
    raceResults.setDriverStandings(List.of(driverStandings));
    return raceResults;
  }
}
