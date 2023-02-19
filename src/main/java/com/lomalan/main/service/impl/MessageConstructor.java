package com.lomalan.main.service.impl;

import com.lomalan.main.Emojis;
import com.lomalan.main.rest.model.f1.DriverStandings;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.weather.Weather;
import com.lomalan.main.rest.model.weather.WeatherInfo;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class MessageConstructor {

  private static final String CELSIUS_SIGN = "°C";
  private static final String DOUBLE_NEW_LINE = "\n\n";

  private MessageConstructor() {
  }

  public static String constructNextRaceMessage(Race nextRace) {
    return getRaceInfo(nextRace);
  }

  public static String constructStandingsMessage(List<DriverStandings> driverStandings) {
    return getDriversStanding(driverStandings);
  }

  public static String constructWeatherMessage(WeatherResponse response, String cityName) {
    Weather weather = response.getWeather().get(0);
    WeatherInfo weatherInfo = response.getWeatherInfo();
    return String.format("%s %s%s", "Current weather in ", cityName, DOUBLE_NEW_LINE)
      + findEmojiForString(weather.getMain()) + "(" + weather.getDescription() +")" + "\n"
      + "Temperature: " + weatherInfo.getTemp() + CELSIUS_SIGN + "\n"
      + "Feels like: " + weatherInfo.getFeelTemp() + CELSIUS_SIGN + "\n"
      + "Humidity: " + weatherInfo.getHumidity() + "%\n\n"
      + "Wind speed: " + response.getWindSpeed() + " m/s \n"
      + "Cloudiness: " + response.getCloudiness() + "%";
  }

  private static String getDriversStanding(List<DriverStandings> driverStandings) {
    return
        "Current drivers standing \n\n"
        + "POS NAME POINTS \n\n"
        + driverStandings.stream()
            .map(MessageConstructor::toStringValue)
            .collect(Collectors.joining("\n"));
  }

  private static String toStringValue(DriverStandings driverStandings) {
    return driverStandings.getPosition().concat(". ")
        .concat(driverStandings.getDriver().getCode()).concat(StringUtils.SPACE)
        .concat(String.valueOf(driverStandings.getPoints()));
  }

  private static String getRaceInfo(Race race) {
    Map<String, LocalDateTime> eventMap = getEventMap(race);
    
    return resolveTitle(race)      
      + "Race date: " + race.getDate() + "\n\n"
      + parseEvents(eventMap) + "\n\n"
      + "City: " + race.getCircuit().getLocation().getLocality() + "\n\n"
      + "Circuit info: " + race.getCircuit().getUrl() + "\n\n"
      + "Race wiki info: " + race.getUrl();
  }

  private static String parseEvents(Map<String, LocalDateTime> eventMap) {
    return eventMap.entrySet().stream()
      .map(entry -> getUkrainianTimeForEvent(entry.getValue(), entry.getKey()))
      .collect(Collectors.joining("\n\n")); 
  }

  private static Map<String, LocalDateTime> getEventMap(Race race) {
    HashMap<String, LocalDateTime> eventToTimeMap = new LinkedHashMap<>();
    eventToTimeMap.put("First Practice", race.getFirstPractice().getDateTime());
    eventToTimeMap.put("Second Practice", race.getSecondPractice().getDateTime());
    if (race.getThirdPractice() != null) {
      eventToTimeMap.put("Third Practice", race.getThirdPractice().getDateTime());
    }
    eventToTimeMap.put("Qualification", race.getQuali().getDateTime());
    if (race.getSprint() != null) {
      eventToTimeMap.put("Sprint", race.getSprint().getDateTime());
    }
    eventToTimeMap.put("Race", race.getDateTime());
    return eventToTimeMap;
  }

  private static String getUkrainianTimeForEvent(LocalDateTime eventDateTime, String eventName) {
    return Emojis.UKRAINE.getUnicodeString() + eventName + " time: " + formatTimeAndDate(eventDateTime);
  }

  private static String resolveTitle(Race race) {
    String emojiUnicodeValue = findEmojiForString(race.getCircuit().getLocation().getCountry());
    return String.format("%s%s%s%s", emojiUnicodeValue, race.getRaceName(), emojiUnicodeValue, "\n\n");
  }

  private static String findEmojiForString(String value) {
    return Arrays.stream(Emojis.values())
        .filter(emojis -> emojis.name().equalsIgnoreCase(value))
        .map(Emojis::getUnicodeString)
        .findFirst()
        .orElse(Emojis.F1.getUnicodeString());
  }

  private static String formatTimeAndDate(LocalDateTime raceDateTime) {
    ZoneId uaTimeZone = ZoneId.of("Europe/Kiev");
    ZoneId utc = ZoneId.of("UTC");

    return raceDateTime.atZone(utc).withZoneSameInstant(uaTimeZone)
        .toLocalTime().toString();
  }
}
