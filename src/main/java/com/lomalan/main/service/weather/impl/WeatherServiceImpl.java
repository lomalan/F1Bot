package com.lomalan.main.service.weather.impl;

import com.lomalan.main.bot.SportNewsBot;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.f1.F1SchedulesRestClient;
import com.lomalan.main.rest.client.weather.WeatherRestClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.weather.WeatherService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

  private final F1SchedulesRestClient f1SchedulesRestClient;
  private final WeatherRestClient weatherRestClient;
  private final TelegramUserRepository userRepository;
  private final SportNewsBot sportNewsBot;

  @Override
  public void getCurrentWeather() {
    scheduleWeatherUpdates();
  }


  @Scheduled(initialDelay = 10000, fixedDelay = 30000)
  public void scheduleWeatherUpdates() {
    log.info("Start to search subscribers on weather updates.... ");
    List<TelegramUser> telegramUsers = userRepository.findAll();
    Race nextRace = f1SchedulesRestClient.getNextRace();
    String cityName = nextRace.getCircuit().getLocation().getLocality();
    WeatherResponse weather = weatherRestClient.getWeatherOnRaceLocation(cityName);
    telegramUsers.stream()
        .filter(TelegramUser::isSubscribedOnWeather)
        .forEach(user -> executeMessage(weather, user));
  }

  private void executeMessage(WeatherResponse weather, TelegramUser user) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(user.getChatId());
    sendMessage.setText(MessageConstructor.constructWeatherMessage(weather));
    try {
      sportNewsBot.executeMethod(sendMessage);
    } catch (TelegramApiException e) {
      log.error(e.getMessage());
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
