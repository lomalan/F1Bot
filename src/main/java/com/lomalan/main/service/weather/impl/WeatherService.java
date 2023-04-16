package com.lomalan.main.service.weather.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.client.weather.WeatherClient;
import com.lomalan.main.rest.model.f1.Location;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.message.MessageExecutor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** Processing current weather data during the race weekend */
@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

  private final F1SchedulesClient f1SchedulesRestClient;
  private final WeatherClient weatherRestClient;
  private final TelegramUserRepository userRepository;
  private final MessageExecutor messageExecutor;

  @Scheduled(cron = "0 0/30 8-20 * * FRI-SUN")
  public void scheduleWeatherUpdates() {
    log.info("Start to search subscribers on weather updates.... ");
    List<TelegramUser> telegramUsers = getWeatherSubscribers();
    if (telegramUsers.isEmpty()) {
      return;
    }
    Race nextRace = f1SchedulesRestClient.getNextRace();
    if (isInvalidDateForWeatherPosting(nextRace)) {
      return;
    }
    Location location = nextRace.getCircuit().getLocation();
    WeatherResponse weather = weatherRestClient.getWeatherOnRaceLocation(location);
    telegramUsers.forEach(user -> executeMessage(location, weather, user));
  }

  private void executeMessage(Location location, WeatherResponse weather, TelegramUser user) {
    messageExecutor.executeMessage(
        user, MessageConstructor.constructWeatherMessage(weather, location.getLocality()));
  }

  private boolean isInvalidDateForWeatherPosting(Race nextRace) {
    return nextRace.isInvalidDateForWeatherPosting()
        && nextRace.getQuali().isInvalidDateForWeatherPosting()
        && (nextRace.getSprint() == null || nextRace.getSprint().isInvalidDateForWeatherPosting());
  }

  private List<TelegramUser> getWeatherSubscribers() {
    return userRepository
        .findAll()
        .stream()
        .filter(TelegramUser::isSubscribedOnWeather)
        .collect(Collectors.toList());
  }
}
