package com.lomalan.main.service.drivers;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.rest.client.f1.F1StandingsClient;
import com.lomalan.main.rest.model.f1.DriverStandings;
import com.lomalan.main.service.MessageService;
import com.lomalan.main.service.impl.MessageConstructor;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
@Slf4j
public class DriversService implements MessageService {

  private final F1StandingsClient f1StandingsRestClient;

  @Override
  public Optional<MessageContainer> processMessage(Update update, TelegramUser user) {
    if (!BotCommands.DRIVERS_STANDING.getCommandName().equals(update.getMessage().getText())) {
      return Optional.empty();
    }
    return Optional.of(new MessageContainer(MessageConstructor.constructStandingsMessage(getCurrentSeasonStandings())));
  }

  private List<DriverStandings> getCurrentSeasonStandings() {
    try {
      return f1StandingsRestClient.getDriverStandings();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return Collections.emptyList();
  }
}
