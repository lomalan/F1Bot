package com.lomalan.main.service.subscriptions.util;

import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramUserConstructor {

  /** Private constructor to avoid instantiation of class */
  private TelegramUserConstructor() {}

  public static TelegramUser prepareUserToSave(User user, BotState state) {
    return TelegramUser.builder()
        .userName(user.getUserName())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .chatId(String.valueOf(user.getId()))
        .isBot(user.getIsBot())
        .state(state)
        .build();
  }

}
