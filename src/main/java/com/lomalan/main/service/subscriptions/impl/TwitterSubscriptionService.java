package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.model.CommandNames;
import org.springframework.stereotype.Service;

@Service
public class TwitterSubscriptionService extends AbstractSubscriptionsService {

  private static final String SUB_MESSAGE = "You're successfully subscribed on twitter updates of f1 official page.";
  private static final String UNSUB_MESSAGE = "You're successfully unsubscribed from twitter updates.";

  public TwitterSubscriptionService(TelegramUserRepository userRepository) {
    super(userRepository);
  }

  @Override
  CommandNames getCommandName() {
    return new CommandNames(BotCommands.SUB_TWITTER.getCommandName(), BotCommands.UNSUB_TWITTER.getCommandName());
  }

  @Override
  String subUser(TelegramUser user) {
    user.setSubscribedOnTwitterUpdates(true);
    return SUB_MESSAGE;
  }

  @Override
  String unsubUser(TelegramUser user) {
    user.setSubscribedOnTwitterUpdates(false);
    return UNSUB_MESSAGE;
  }

  @Override
  public boolean isUserSubscribed(TelegramUser user) {
    return user.isSubscribedOnTwitterUpdates();
  }
}