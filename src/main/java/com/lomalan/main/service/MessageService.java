package com.lomalan.main.service;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageService {

  Optional<String> processMessage(Update update);

  String getCurrentCommand(Update update);

}
