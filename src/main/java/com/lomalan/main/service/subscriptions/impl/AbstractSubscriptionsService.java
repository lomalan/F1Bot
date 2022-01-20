package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.MessageService;
import com.lomalan.main.service.subscriptions.SubscriptionsService;
import com.lomalan.main.service.subscriptions.model.CommandNames;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public abstract class AbstractSubscriptionsService implements SubscriptionsService, MessageService {

  private final TelegramUserRepository userRepository;

  private static final String ALREADY_SUB = "You're already subscribed";

  abstract CommandNames getCommandName();

  abstract String subUser(TelegramUser user);

  abstract String unsubUser(TelegramUser user);

  abstract boolean isUserSubscribed(TelegramUser user);

  @Override
  public Optional<MessageContainer> processMessage(Update update, TelegramUser user) {
    String subscribeResult = subscribe(update, user);
    String unsubscribeResult = unsubscribe(update, user);
    if (subscribeResult.isEmpty() && unsubscribeResult.isEmpty()) {
      return Optional.empty();
    }

    userRepository.save(user);
    if (subscribeResult.isEmpty()) {
      return Optional.of(new MessageContainer(unsubscribeResult));
    }
    return Optional.of(new MessageContainer(subscribeResult));
  }

  @Override
  public String getCurrentCommand(TelegramUser user) {
    if (isUserSubscribed(user)) {
      return getCommandName().getSubCommandName();
    }
    return getCommandName().getUnsubCommandName();
  }

  @Override
  public String subscribe(Update update, TelegramUser telegramUser) {
    if (!getCommandName().getSubCommandName().equals(update.getMessage().getText())) {
      return StringUtils.EMPTY;
    }

    return isUserSubscribed(telegramUser)
        ? ALREADY_SUB
        : subUser(telegramUser);
  }

  @Override
  public String unsubscribe(Update update, TelegramUser telegramUser) {
    if (!getCommandName().getUnsubCommandName().equals(update.getMessage().getText())) {
      return StringUtils.EMPTY;
    }

    return unsubUser(telegramUser);
  }

}
