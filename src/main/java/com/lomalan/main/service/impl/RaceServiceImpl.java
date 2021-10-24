package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.service.RaceService;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class RaceServiceImpl implements RaceService {

  private final ScheduleService scheduleService;

  public RaceServiceImpl(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Override
  public Optional<PartialBotApiMethod<Message>> getRaceData(Update update) {
    if (update.getMessage().getText().equals(BotCommands.NEXT_RACE.getCommandName())) {
      return Optional.of(scheduleService.getNextRace(update));
    }
    return Optional.empty();
  }
}
