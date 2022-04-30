package com.lomalan.main.rest.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Race {
  private String season;
  private String round;
  private String url;
  private String raceName;
  private String date;
  private String time;
  @JsonProperty("Circuit")
  private Circuit circuit;

  public boolean isValidDateForLiveTimingPosting() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime raceDateTime = getRaceDateTime();
    return now.isAfter(raceDateTime) && now.isBefore(raceDateTime.plusHours(2));
  }

  public boolean isValidDateForWeatherPosting() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime raceDateTime = getRaceDateTime();
    return now.isAfter(raceDateTime.minusHours(2)) && raceDateTime.isAfter(now);
  }

  public LocalDateTime getRaceDateTime() {
    return parseToLocalDateTime(date, time);
  }

  private LocalDateTime parseToLocalDateTime(String date, String time) {
    return LocalDateTime.parse(StringUtils.substringBefore(date.concat("T").concat(time), "Z"));
  }
}
