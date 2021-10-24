package com.lomalan.main.service.impl;

import com.lomalan.main.Emojis;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.livetiming.DriverInfo;
import com.lomalan.main.rest.model.livetiming.LiveTimingInfo;
import com.lomalan.main.rest.model.weather.Weather;
import com.lomalan.main.rest.model.weather.WeatherInfo;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class MessageConstructor {

  private static final String CELSIUS_SIGN = "\u00B0C";

  private MessageConstructor() {
  }

  public static String constructNextRaceMessage(Race nextRace) {
    return getRaceInfo(nextRace);
  }

  public static String constructWeatherMessage(WeatherResponse response) {
    return getWeatherInfo(response);
  }

  public static String constructMessage(LiveTimingInfo liveTimingInfo) {
    return  liveTimingInfo.getRaceName().concat("\n\n")
        .concat(liveTimingInfo.getLapStatus()).concat("\n\n")
        .concat(liveTimingInfo.getDriverInfo().stream()
            .map(MessageConstructor::processDriverData)
            .collect(Collectors.joining("\n")));
  }


  private static String processDriverData(DriverInfo info) {
    return info.getPosition().concat(". ")
        .concat(info.getName()).concat(" ")
        .concat(info.getGap().equals("") ? "+0.000" : info.getGap()).concat(" ")
        .concat(info.getStops());
  }
  private static String getWeatherInfo(WeatherResponse response) {
    Weather weather = response.getWeather().get(0);
    String cityName = response.getCityName();
    WeatherInfo weatherInfo = response.getWeatherInfo();
    return "Current weather in " + cityName +  "\n\n"
        + findEmojiForString(weather.getMain()) + "(" + weather.getDescription() +")" + "\n"
        + "Temperature: " + weatherInfo.getTemp() + CELSIUS_SIGN + "\n"
        + "Feels like: " + weatherInfo.getFeelTemp() + CELSIUS_SIGN + "\n"
        + "Humidity: " + weatherInfo.getHumidity() + "%\n\n"
        + "Wind speed: " + response.getWindSpeed() + " m/s \n"
        + "Cloudiness: " + response.getCloudiness() + "%";
  }

  private static String getRaceInfo(Race race) {
    return resolveTitle(race) + "\n\n"
        + "Race date: " + race.getDate() + "\n\n"
        + Emojis.GB.getUnicodeString() + "London time: " + StringUtils.substringBefore(race.getTime(), ":00Z") + "\n\n"
        + Emojis.UKRAINE.getUnicodeString() + "Kyiv time: " + formatTimeAndDate(race.getDate(), race.getTime()) + "\n\n"
        + "City: " + race.getCircuit().getLocation().getLocality() + "\n\n"
        + "Circuit info: " + race.getCircuit().getUrl() + "\n\n"
        + "Race wiki info: " + race.getUrl();
  }

  private static String resolveTitle(Race race) {
    String emojiUnicodeValue = findEmojiForString(race.getCircuit().getLocation().getCountry());
    return emojiUnicodeValue + race.getRaceName() + emojiUnicodeValue;
  }

  private static String findEmojiForString(String value) {
    return Arrays.stream(Emojis.values())
        .filter(emojis -> emojis.name().equalsIgnoreCase(value))
        .map(Emojis::getUnicodeString)
        .findFirst()
        .orElse(Emojis.F1.getUnicodeString());
  }

  private static String formatTimeAndDate(String date, String time) {
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(date + "T" + time);

    ZoneId uaTimeZone = ZoneId.of("Europe/Kiev");
    return zonedDateTime.withZoneSameInstant(uaTimeZone).toLocalDateTime().toLocalTime().toString();
  }
}
