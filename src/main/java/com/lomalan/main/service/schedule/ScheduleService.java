package com.lomalan.main.service.schedule;

import com.lomalan.main.model.MessageContainer;
import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ScheduleService {

  Optional<MessageContainer> getNextRace(Update update);
}
