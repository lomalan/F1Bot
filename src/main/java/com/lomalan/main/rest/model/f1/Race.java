package com.lomalan.main.rest.model.f1;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Race {
  private String season;
  private String round;
  private String url;
  private String raceName;
  private String date;
  private String time;

  @SerializedName("Circuit")
  private Circuit circuit;

  @SerializedName("FirstPractice")
  private Event firstPractice;

  @SerializedName("SecondPractice")
  private Event secondPractice;

  @SerializedName("ThirdPractice")
  private Event thirdPractice;

  @SerializedName("Qualifying")
  private Event quali;

  @SerializedName("Sprint")
  private Event sprint;

  public boolean isInvalidDateForLiveTimingPosting() {
    return Event.builder().date(date).time(time).build().isInvalidDateForLiveTimingPosting();
  }

  public boolean isInvalidDateForWeatherPosting() {
    return Event.builder().date(date).time(time).build().isInvalidDateForWeatherPosting();
  }

  public LocalDateTime getDateTime() {
    return Event.builder().date(date).time(time).build().getDateTime();
  }
}
