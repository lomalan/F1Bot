package com.lomalan.main.service.weather.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.f1.F1SchedulesRestClient;
import com.lomalan.main.rest.client.weather.WeatherRestClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.message.MessageExecutor;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherServiceImpl {

  private final F1SchedulesRestClient f1SchedulesRestClient;
  private final WeatherRestClient weatherRestClient;
  private final TelegramUserRepository userRepository;
  private final MessageExecutor messageExecutor;

  @Scheduled(cron = "0 0/30 8-18 * * FRI-SUN")
  public void scheduleWeatherUpdates() {
    log.info("Start to search subscribers on weather updates.... ");
    List<TelegramUser> telegramUsers = userRepository.findAll();
    Race nextRace = f1SchedulesRestClient.getNextRace();
    String cityName = nextRace.getCircuit().getLocation().getLocality();
    WeatherResponse weather = weatherRestClient.getWeatherOnRaceLocation(cityName);
    telegramUsers.stream()
        .filter(TelegramUser::isSubscribedOnWeather)
        .forEach(user -> messageExecutor.executeMessage(user, MessageConstructor.constructWeatherMessage(weather)));
  }
}
