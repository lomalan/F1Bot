package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.MessageService;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
@Slf4j
public class RaceServiceImpl implements MessageService {

  private final ScheduleService scheduleService;

  @Override
  public Optional<MessageContainer> processMessage(Update update) {
    return getRaceData(update);
  }

  @Override
  public String getCurrentCommand(Update update) {
    return StringUtils.EMPTY;
  }

  private Optional<MessageContainer> getRaceData(Update update) {
    if (BotCommands.NEXT_RACE.getCommandName().equals(update.getMessage().getText())) {
      return scheduleService.getNextRace(update);
    }
    return Optional.empty();
  }
}
