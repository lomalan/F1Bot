package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.service.PartialBotMethodService;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
@Slf4j
public class RaceServiceImpl implements PartialBotMethodService {

  private final ScheduleService scheduleService;

  @Override
  public Optional<PartialBotApiMethod<Message>> getPartialBotApiMethod(Update update) {
    return getRaceData(update);
  }

  private Optional<PartialBotApiMethod<Message>> getRaceData(Update update) {
    if (update.getMessage().getText().equals(BotCommands.NEXT_RACE.getCommandName())) {
      return Optional.of(scheduleService.getNextRace(update));
    }
    return Optional.empty();
  }
}
