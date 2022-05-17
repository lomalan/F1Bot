package com.lomalan.main.rest.model.f1;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class Event {
  private String date;
  private String time;

  public boolean isValidDateForLiveTimingPosting() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime raceDateTime = getDateTime();
    return now.isAfter(raceDateTime) && now.isBefore(raceDateTime.plusHours(2));
  }

  public boolean isValidDateForWeatherPosting() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime raceDateTime = getDateTime();
    return now.isAfter(raceDateTime.minusHours(2)) && raceDateTime.isAfter(now);
  }

  public LocalDateTime getDateTime() {
     return parseToLocalDateTime(date, time);
  }
    
  private LocalDateTime parseToLocalDateTime(String date, String time) {
    return LocalDateTime.parse(StringUtils.substringBefore(date.concat("T").concat(time), "Z"));
  }
}
