package com.lomalan.main.service.schedule;

import com.lomalan.main.model.MessageContainer;
import java.util.Optional;

public interface ScheduleService {

  Optional<MessageContainer> getNextRace();
}
