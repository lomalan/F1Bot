package com.lomalan.main.rest.weather;


import com.lomalan.main.TestConfig;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.model.f1.Race;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class, F1SchedulesClient.class})
public class F1SchedulesClientTest {

  @Autowired
  private F1SchedulesClient testObject;

  @Test
  public void testNextRace() throws Exception {
    Race nextRace = testObject.getNextRace();
    assertEquals("2023", nextRace.getSeason());
  }

}
