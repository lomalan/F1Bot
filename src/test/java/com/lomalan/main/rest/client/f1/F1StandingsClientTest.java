package com.lomalan.main.rest.client.f1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lomalan.main.TestConfig;
import com.lomalan.main.rest.model.f1.DriverStandings;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfig.class, F1StandingsClient.class })
public class F1StandingsClientTest {

    @Autowired
    private F1StandingsClient testObject;

    @Test
    public void testDriverStandings() throws Exception {
        List<DriverStandings> driverStandings = testObject.getDriverStandings();
        assertTrue(driverStandings.size() >= 20);
    }
}
