package com.lomalan.main.service;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Sends Messages to process by facade service
 */
public interface MessageService {

  Optional<String> processMessage(Update update);

  /**
   * Returns command which was used to execute service
   */
  String getCurrentCommand(Update update);

}
