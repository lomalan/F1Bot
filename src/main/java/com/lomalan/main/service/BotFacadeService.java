package com.lomalan.main.service;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotFacadeService {

  /**
   * Forms messages from received updates from users
   */
  PartialBotApiMethod<Message> processUpdateWithMessage(Update update);
}
