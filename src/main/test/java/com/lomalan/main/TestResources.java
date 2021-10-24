package com.lomalan.main;

import static org.junit.jupiter.api.Assertions.fail;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestResources {

  /**
   * Prevents instantiation of this utility class.
   */
  private TestResources() {
  }

  public static Path driverStandingsExample() {
    return pathOfResource("driverStandingsExample.json");
  }

  private static Path pathOfResource(String resourceName) {
    try {
      URL resource = TestResources.class.getClassLoader().getResource(resourceName);
      return Paths.get(resource.toURI());
    } catch (URISyntaxException e) {
      return fail(e);
    }
  }
}
