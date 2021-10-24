package com.lomalan.main.service.subscriptions.impl;

import static com.lomalan.main.service.subscriptions.util.SubUtils.prepareUserToSave;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.SubscriptionsService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@AllArgsConstructor
public class LiveTimingSubscriptionsService implements SubscriptionsService {

  private final TelegramUserRepository userRepository;

  @Override
  public Optional<java.lang.String> subscribe(Update update) {
    if (!update.getMessage().getText().equals(BotCommands.SUB_LIVE.getCommandName())) {
      return Optional.empty();
    }

    User user = update.getMessage().getFrom();
    if (isSubscribed(update)) {
      return Optional.of("You're already subscribed");
    }
    TelegramUser telegramUser = userRepository.findByChatId(java.lang.String.valueOf(user.getId()));
    if (telegramUser == null) {
      userRepository.save(prepareUserToSave(user, false, true));
    } else {
      telegramUser.setSubscribedOnLiveUpdates(true);
      userRepository.save(telegramUser);
    }

    return Optional.of("You're successfully subscribed on live updates.\n\n"
        +    "You will receive every 5 minutes race updates when it will start");
  }

  @Override
  public Optional<java.lang.String> unsubscribe(Update update) {
    if (!update.getMessage().getText().equals(BotCommands.UNSUB_LIVE.getCommandName())) {
      return Optional.empty();
    }

    User user = update.getMessage().getFrom();
    TelegramUser telegramUser = userRepository.findByChatId(java.lang.String.valueOf(user.getId()));
    if (telegramUser == null) {
      return Optional.of("You're not subscribed!");
    }
    telegramUser.setSubscribedOnLiveUpdates(false);
    userRepository.save(telegramUser);

    return Optional.of("You're successfully unsubscribed from live updates.");
  }

  public String getCurrentCommand(Update update) {
    if (isSubscribed(update)) {
      return BotCommands.SUB_LIVE.getCommandName();
    }
    return BotCommands.UNSUB_LIVE.getCommandName();
  }

  @Override
  public boolean isSubscribed(Update update) {
    TelegramUser user = userRepository.findByChatId(update.getMessage().getChatId().toString());
    if (user == null) {
      return false;
    }
    return user.isSubscribedOnLiveUpdates();
  }
}
