package com.lomalan.main.service.message;

import com.lomalan.main.dao.model.TelegramUser;

public interface MessageExecutor {
  void executeMessage(TelegramUser user, String messageText);
}
