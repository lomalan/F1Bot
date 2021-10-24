package com.lomalan.main.service;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface PartialBotMethodService {

  Optional<PartialBotApiMethod<Message>> getPartialBotApiMethod(Update update);

}
