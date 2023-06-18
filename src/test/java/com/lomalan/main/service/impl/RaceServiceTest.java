package com.lomalan.main.service.impl;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

class RaceServiceTest {

  private final ScheduleService scheduleService = mock(ScheduleService.class);

  private final RaceService testObject = new RaceService(scheduleService);

  @Test
  void whenCommandIsNotNextRaceReturnEmpty() {
    Optional<MessageContainer> result =
        testObject.processMessage(createUpdate("SomeText"), TelegramUser.builder().build());

    assertTrue(result.isEmpty());
  }

  @Test
  void whenCommandIsNextRaceReturnResult() {
    String expectedResult = "Success";
    Update update = createUpdate(BotCommands.NEXT_RACE.getCommandName());
    Mockito.when(scheduleService.getNextRace())
        .thenReturn(Optional.of(MessageContainer.builder().text(expectedResult).build()));
    Optional<MessageContainer> result =
        testObject.processMessage(update, TelegramUser.builder().build());

    assertTrue(result.isPresent());
    assertEquals(expectedResult, result.get().getText());
  }
}
