package com.lomalan.main.rest.f1;


import com.lomalan.main.TestConfig;
import com.lomalan.main.rest.client.f1.F1StandingsClient;
import com.lomalan.main.rest.model.f1.DriverStandings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class, F1StandingsClient.class})
public class F1StandingsClientTest {

  @Autowired
  private F1StandingsClient testObject;

  @Test
  public void testDriverStandings() throws Exception {
    List<DriverStandings> driverStandings = testObject.getDriverStandings();
       assertTrue(driverStandings.size() >= 20);
  }

}
