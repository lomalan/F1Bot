package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.MessageService;
import com.lomalan.main.service.last.RaceResultsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LastRaceService implements MessageService {

  private final RaceResultsService raceResultsService;

   @Override
  public Optional<MessageContainer> processMessage(Update update, TelegramUser user) {
    return getRaceResultData(update);
  }

  private Optional<MessageContainer> getRaceResultData(Update update) {
    if (BotCommands.RACE_RESULTS.getCommandName().equals(update.getMessage().getText())) {
      return raceResultsService.getRaceResults();
    }
    return Optional.empty();
  }
}
