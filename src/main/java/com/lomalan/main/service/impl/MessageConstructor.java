package com.lomalan.main.service.impl;

import com.lomalan.main.Emojis;
import com.lomalan.main.rest.model.f1.Race;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class MessageConstructor {

  private MessageConstructor() {
  }

  public static String constructNextRaceMessage(Race nextRace) {
    return getRaceInfo(nextRace);
  }


  private static String getRaceInfo(Race race) {
    return resolveTitle(race) + "\n\n"
        + "Race date: " + race.getDate() + "\n\n"
        + "Race time: " + StringUtils.substringBefore(race.getTime(), ":00Z") + "\n\n"
        + Emojis.UKRAINE.getUnicodeString() + "Kyiv time: " + formatTimeAndDate(race.getDate(), race.getTime()) + "\n\n"
        + "Circuit name: " + race.getCircuit().getCircuitName() + "\n\n"
        + "City: " + race.getCircuit().getLocation().getLocality() + "\n\n"
        + "Circuit info: " + race.getCircuit().getUrl() + "\n\n"
        + "Race wiki info: " + race.getUrl();
  }

  private static String resolveTitle(Race race) {
    String emojiUnicodeValue = Arrays.stream(Emojis.values())
        .filter(emojis -> emojis.name().equalsIgnoreCase(race.getCircuit().getLocation().getCountry()))
        .map(Emojis::getUnicodeString)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Emoji for country not found"));

    return emojiUnicodeValue + race.getRaceName() + emojiUnicodeValue;
  }

  private static String formatTimeAndDate(String date, String time) {
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(date + "T" + time);

    ZoneId uaTimeZone = ZoneId.of("Europe/Kiev");
    return zonedDateTime.withZoneSameInstant(uaTimeZone).toLocalDateTime().toLocalTime().toString();
  }
}
