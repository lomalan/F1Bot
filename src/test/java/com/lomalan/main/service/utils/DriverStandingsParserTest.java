package com.lomalan.main.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.lomalan.main.TestResources;
import com.lomalan.main.rest.model.f1.DriverStandings;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Test;

class DriverStandingsParserTest {

    @Test
    void parseDriversStanding() throws IOException {
        String jsonString = getJsonString();
        List<DriverStandings> driverStandings = DriverStandingsParser.parseToDriverStandings(jsonString);

        assertFalse(driverStandings.isEmpty());
        assertEquals(21, driverStandings.size());
        DriverStandings robertKubica = driverStandings.get(19);
        assertEquals(String.valueOf(0), robertKubica.getPoints());
        assertEquals("KUB", robertKubica.getDriver().getCode());
    }

    private String getJsonString() throws IOException {
        byte[] response = Files.readAllBytes(TestResources.driverStandingsExample());
        return new String(response);
    }
}
