package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.model.CommandNames;
import org.springframework.stereotype.Service;

@Service
public class WeatherSubscriptionsService extends AbstractSubscriptionsService {

  private static final String SUB_MESSAGE = "You're successfully subscribed on weather updates.\n\n"
      +    "You will receive every 30 minutes weather updates during the weekend before qualification and race";

  private static final String UNSUB_MESSAGE = "You're successfully unsubscribed from weather updates.";

  public WeatherSubscriptionsService(TelegramUserRepository userRepository) {
    super(userRepository);
  }

  @Override
  CommandNames getCommandName() {
    return new CommandNames(BotCommands.CURRENT_WEATHER.getCommandName(), BotCommands.UNSUB_WEATHER.getCommandName());
  }

  @Override
  void subUser(TelegramUser user) {
    user.setSubscribedOnWeather(true);
  }

  @Override
  void unsubUser(TelegramUser user) {
    user.setSubscribedOnWeather(false);
  }

  @Override
  String getSubMessage() {
    return SUB_MESSAGE;
  }

  @Override
  String getUnsubMessage() {
    return UNSUB_MESSAGE;
  }

  @Override
  public boolean isUserSubscribed(TelegramUser user) {
    return user.isSubscribedOnWeather();
  }
}
