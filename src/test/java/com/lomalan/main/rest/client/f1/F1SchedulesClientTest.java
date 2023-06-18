package com.lomalan.main.rest.client.f1;


import com.lomalan.main.TestConfig;
import com.lomalan.main.rest.model.f1.Race;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class, F1SchedulesClient.class})
class F1SchedulesClientTest {

  @Autowired
  private F1SchedulesClient testObject;

  @Test
  void testNextRace() {
    Race nextRace = testObject.getNextRace();
    assertTrue(Integer.parseInt(nextRace.getSeason()) >= 2023);
  }

}
