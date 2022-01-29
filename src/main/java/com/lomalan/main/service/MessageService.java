package com.lomalan.main.service;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Sends Messages to process by facade service
 */
public interface MessageService {

  Optional<MessageContainer> processMessage(Update update, TelegramUser user);

  /**
   * Returns command which was used to execute service
   */
  default String getCurrentCommand(TelegramUser user) {
    return StringUtils.EMPTY;
  }

}
