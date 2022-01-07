package com.lomalan.main.service.schedule;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ScheduleService {

  PartialBotApiMethod<Message> getNextRace(Update update);
}
