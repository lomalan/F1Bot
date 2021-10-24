package com.lomalan.main.service;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface RaceService {

  Optional<PartialBotApiMethod<Message>> getRaceData(Update update);
}
