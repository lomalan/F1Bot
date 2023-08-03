package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.last.RaceResultsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Optional;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class LastRaceServiceTest {

  private final RaceResultsService raceResultsService = mock(RaceResultsService.class);

  private final LastRaceService testObject = new LastRaceService(raceResultsService);

  @Test
  void whenCommandIsNotLastRaceResultsReturnEmpty() {
    Optional<MessageContainer> result = testObject
      .processMessage(createUpdate("SomeText"), TelegramUser.builder().build());

    assertTrue(result.isEmpty());
  }

  @Test
  void whenCommandIsLastRaceResultsReturnResult() {
    var expectedResult = Optional.of(MessageContainer.builder().text("Success").build());
    Update update = createUpdate(BotCommands.RACE_RESULTS.getCommandName());
    Mockito.when(raceResultsService.getRaceResults()).thenReturn(expectedResult);
    Optional<MessageContainer> result = testObject.processMessage(update, TelegramUser.builder().build());

    assertTrue(result.isPresent());
    assertEquals(expectedResult, result);
  }
}
