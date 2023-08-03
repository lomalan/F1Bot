package com.lomalan.main.rest.model.f1;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class Event {
  private String date;
  private String time;

  public boolean isInvalidDateForLiveTimingPosting() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime raceDateTime = getDateTime();
    return !now.isAfter(raceDateTime) || !now.isBefore(raceDateTime.plusHours(2));
  }

  public boolean isInvalidDateForWeatherPosting() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime raceDateTime = getDateTime();
    return !now.isAfter(raceDateTime.minusHours(2)) || !raceDateTime.isAfter(now);
  }

  public LocalDateTime getDateTime() {
    return parseToLocalDateTime(date, time);
  }

  private LocalDateTime parseToLocalDateTime(String date, String time) {
    return LocalDateTime.parse(StringUtils.substringBefore(date.concat("T").concat(time), "Z"));
  }
}
