package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.rest.client.f1.F1StandingsRestClient;
import com.lomalan.main.rest.model.f1.DriverStandings;
import com.lomalan.main.service.RaceService;
import com.lomalan.main.service.schedule.ScheduleService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
@Slf4j
public class RaceServiceImpl implements RaceService {

  private final ScheduleService scheduleService;
  private final F1StandingsRestClient f1StandingsRestClient;

  @Override
  public Optional<PartialBotApiMethod<Message>> getRaceData(Update update) {
    if (update.getMessage().getText().equals(BotCommands.NEXT_RACE.getCommandName())) {
      return Optional.of(scheduleService.getNextRace(update));
    }
    return Optional.empty();
  }

  @Override
  public Optional<PartialBotApiMethod<Message>> getDriversStanding(Update update) {
    if (!update.getMessage().getText().equals(BotCommands.DRIVERS_STANDING.getCommandName())) {
      return Optional.empty();
    }
    try {
      List<DriverStandings> currentStandings = f1StandingsRestClient.getDriverStandings();
      SendMessage sendMessage = new SendMessage();
      sendMessage.setChatId(update.getMessage().getChatId().toString());
      sendMessage.setText(MessageConstructor.constructStandingsMessage(currentStandings));
      return Optional.of(sendMessage);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return Optional.empty();
  }
}
