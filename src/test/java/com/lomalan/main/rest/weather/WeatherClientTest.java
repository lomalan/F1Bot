package com.lomalan.main.rest.weather;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lomalan.main.TestConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomalan.main.TestResources;
import com.lomalan.main.rest.client.weather.WeatherClient;
import com.lomalan.main.rest.model.f1.Location;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.nio.file.Files;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class, WeatherClient.class})
public class WeatherClientTest {

  @Autowired
  private WeatherClient testObject;

  @Test
  public void testAll2023Cities() throws Exception {
    List<Location> locations = getLocationsInfo();
    locations.forEach(location -> {
      WeatherResponse weather = testObject.getWeatherOnRaceLocation(location);
      assertEquals(location.getLatitude(), weather.getCoordinates().getLat());
    });
  }

  private List<Location> getLocationsInfo() throws Exception {
    var objectMapper = new ObjectMapper();
    byte[] response = Files.readAllBytes(TestResources.locationInfo());
    return objectMapper.readValue(response, new TypeReference<>() {});
  }
}
