package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.SubscriptionsService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {

  private final TelegramUserRepository userRepository;

  public SubscriptionsServiceImpl(TelegramUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<String> subscribe(Update update) {
    if (!update.getMessage().getText().equals(BotCommands.CURRENT_WEATHER.getCommandName())) {
      return Optional.empty();
    }

    User user = update.getMessage().getFrom();
    if (isSubscribed(update)) {
      return Optional.of("You're already subscribed");
    }
    TelegramUser telegramUser = userRepository.findByChatId(String.valueOf(user.getId()));
    if (telegramUser == null) {
      userRepository.save(prepareUserToSave(user, true, false));
    } else {
      telegramUser.setSubscribedOnWeather(true);
      userRepository.save(telegramUser);
    }

    return Optional.of("You're successfully subscribed on weather updates.\n\n "
            +    "You will receive every hour weather updates during the weekend before qualification and race");
  }

  @Override
  public Optional<String> unsubscribe(Update update) {
    if (!update.getMessage().getText().equals(BotCommands.UNSUB_WEATHER.getCommandName())) {
      return Optional.empty();
    }

    User user = update.getMessage().getFrom();
    TelegramUser telegramUser = userRepository.findByChatId(String.valueOf(user.getId()));
    if (telegramUser == null) {
      return Optional.of("You're not subscribed!");
    }
    telegramUser.setSubscribedOnWeather(false);
    userRepository.save(telegramUser);

    return Optional.of("You're successfully unsubscribed from weather updates.");
  }

  @Override
  public boolean isSubscribed(Update update) {
    TelegramUser user = userRepository.findByChatId(update.getMessage().getChatId().toString());
    if (user == null) {
      return false;
    }
    return user.isSubscribedOnWeather();
  }


  private TelegramUser prepareUserToSave(User user,
      boolean isSubscribedOnWeather,
      boolean isSubscribedOnLiveUpdates) {
    return TelegramUser.builder()
        .userName(user.getUserName())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .chatId(String.valueOf(user.getId()))
        .isBot(user.getIsBot())
        .subscribedOnWeather(isSubscribedOnWeather)
        .subscribedOnLiveUpdates(isSubscribedOnLiveUpdates)
        .build();
  }
}
