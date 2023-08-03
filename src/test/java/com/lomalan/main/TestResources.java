package com.lomalan.main;

import static org.junit.jupiter.api.Assertions.fail;

import com.lomalan.main.rest.model.f1.Circuit;
import com.lomalan.main.rest.model.f1.Event;
import com.lomalan.main.rest.model.f1.Location;
import com.lomalan.main.rest.model.f1.Race;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public final class TestResources {

  /** Prevents instantiation of this utility class. */
  private TestResources() {}

  public static Path driverStandingsExample() {
    return pathOfResource("driverStandingsExample.json");
  }

  public static Path locationInfo() {
    return pathOfResource("countryCodeCity.json");
  }

  private static Path pathOfResource(String resourceName) {
    try {
      URL resource = TestResources.class.getClassLoader().getResource(resourceName);
      return Paths.get(resource.toURI());
    } catch (URISyntaxException e) {
      return fail(e);
    }
  }

  public static Update createUpdate(String command) {
    Update update = new Update();
    Message message = new Message();
    message.setText(command);
    Chat chat = new Chat();
    chat.setId(1L);
    message.setChat(chat);
    update.setMessage(message);
    return update;
  }

  public static Race createNextRace(LocalDateTime raceDate) {
    String date = raceDate.toLocalDate().toString();
    String time = raceDate.toLocalTime().toString();
    Event event = Event.builder().date(date).time(time).build();
    return Race.builder()
        .date(date)
        .time(time)
        .quali(event)
        .firstPractice(event)
        .secondPractice(event)
        .thirdPractice(event)
        .circuit(Circuit.builder().location(createLocation()).build())
        .build();
  }

  public static Location createLocation() {
    return Location.builder().locality("Australia").country("Some country").build();
  }

  public static Location createLocationWithCustomData(String locality, String country) {
    return Location.builder().locality(locality).country(country).build();
  }
}
