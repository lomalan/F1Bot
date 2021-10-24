package com.lomalan.main.service.subscriptions.util;

import com.lomalan.main.dao.model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.User;

public class SubUtils {

  /** Private constructor to avoid instantiation of class */
  private SubUtils() {}

  public static TelegramUser prepareUserToSave(
      User user,
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
