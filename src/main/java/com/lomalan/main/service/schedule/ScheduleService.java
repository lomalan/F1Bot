package com.lomalan.main.service.schedule;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ScheduleService {

  SendPhoto getNextRace(Update update);
}
