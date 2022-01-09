package com.lomalan.main.service.subscriptions.impl;

import static com.lomalan.main.service.subscriptions.util.SubUtils.prepareUserToSave;

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
import org.telegram.telegrambots.meta.api.objects.User;

@AllArgsConstructor
public abstract class AbstractSubscriptionsService implements SubscriptionsService, MessageService {

  private final TelegramUserRepository userRepository;

  private static final String ALREADY_SUB = "You're already subscribed";
  private static final String NOT_SUB = "You're not subscribed";

  abstract CommandNames getCommandName();

  abstract void subUser(TelegramUser user);

  abstract void unsubUser(TelegramUser user);

  abstract String getSubMessage();

  abstract String getUnsubMessage();

  abstract boolean isUserSubscribed(TelegramUser user);

  @Override
  public Optional<MessageContainer> processMessage(Update update) {
    String subscribeResult = subscribe(update);
    String unsubscribeResult = unsubscribe(update);
    if (subscribeResult.isEmpty() && unsubscribeResult.isEmpty()) {
      return Optional.empty();
    }
    if (subscribeResult.isEmpty()) {
      return Optional.of(new MessageContainer(unsubscribeResult));
    }
    return Optional.of(new MessageContainer(subscribeResult));
  }

  public String getCurrentCommand(Update update) {
    if (isSubscribed(update)) {
      return getCommandName().getSubCommandName();
    }
    return getCommandName().getUnsubCommandName();
  }

  @Override
  public String subscribe(Update update) {
    if (!getCommandName().getSubCommandName().equals(update.getMessage().getText())) {
      return StringUtils.EMPTY;
    }

    User user = update.getMessage().getFrom();
    if (isSubscribed(update)) {
      return ALREADY_SUB;
    }
    TelegramUser telegramUser = userRepository.findByChatId(String.valueOf(user.getId()));
    if (telegramUser == null) {
      TelegramUser userToSave = prepareUserToSave(user);
      subUser(userToSave);
      userRepository.save(userToSave);
    } else {
      subUser(telegramUser);
      userRepository.save(telegramUser);
    }

    return getSubMessage();
  }

  @Override
  public String unsubscribe(Update update) {
    if (!getCommandName().getUnsubCommandName().equals(update.getMessage().getText())) {
      return StringUtils.EMPTY;
    }

    User user = update.getMessage().getFrom();
    TelegramUser telegramUser = userRepository.findByChatId(String.valueOf(user.getId()));
    if (telegramUser == null) {
      return NOT_SUB;
    }
    unsubUser(telegramUser);
    userRepository.save(telegramUser);

    return getUnsubMessage();
  }

  @Override
  public boolean isSubscribed(Update update) {
    TelegramUser user = userRepository.findByChatId(update.getMessage().getChatId().toString());
    if (user == null) {
      return false;
    }
    return isUserSubscribed(user);
  }

}
