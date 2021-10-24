package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.model.CommandNames;
import org.springframework.stereotype.Service;

@Service
public class LiveTimingSubscriptionsService extends AbstractSubscriptionsService {

  private static final String SUB_MESSAGE = "You're successfully subscribed on live updates.\n\n"
      +    "You will receive every 5 minutes race updates when it will start";

  private static final String UNSUB_MESSAGE = "You're successfully unsubscribed from live updates.";

  public LiveTimingSubscriptionsService(TelegramUserRepository userRepository) {
    super(userRepository);
  }

  @Override
  CommandNames getCommandName() {
    return new CommandNames(BotCommands.SUB_LIVE.getCommandName(), BotCommands.UNSUB_LIVE.getCommandName());
  }

  @Override
  void subUser(TelegramUser user) {
    user.setSubscribedOnLiveUpdates(true);
  }

  @Override
  void unsubUser(TelegramUser user) {
    user.setSubscribedOnLiveUpdates(false);
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
  boolean isUserSubscribed(TelegramUser user) {
    return user.isSubscribedOnLiveUpdates();
  }
}
