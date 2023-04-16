package com.lomalan.main.service.schedule.impl;

import static com.lomalan.main.TestResources.createLocationWithCustomData;
import static com.lomalan.main.TestResources.createNextRace;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.model.f1.Circuit;
import com.lomalan.main.rest.model.f1.Race;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScheduleServiceImplTest {

  private F1SchedulesClient f1SchedulesClient;
  private ScheduleServiceImpl testObject;

  @BeforeEach
  void setup() {
    f1SchedulesClient = Mockito.mock(F1SchedulesClient.class);
    testObject = new ScheduleServiceImpl(f1SchedulesClient);
  }

  @Test
  void whenThereAreSchedulesAvailableWithoutPictureThenReturnOnlyMessage() {
    Race nextRace = createNextRace(LocalDateTime.of(2021, Month.APRIL, 15, 12, 0, 0));
    Mockito.when(f1SchedulesClient.getNextRace()).thenReturn(nextRace);

    Optional<MessageContainer> resultMessageContainer = testObject.getNextRace();
    assertTrue(resultMessageContainer.isPresent());
    assertNull(resultMessageContainer.get().getPhoto());
    assertNotNull(resultMessageContainer.get().getText());
  }

  @Test
  void whenThereAreSchedulesAvailableWithPictureThenReturnMessageWithPhoto() {
    Race nextRace = createNextRace(LocalDateTime.of(2021, Month.APRIL, 15, 12, 0, 0));
    nextRace.setCircuit(
        Circuit.builder().location(createLocationWithCustomData("Abu Dhabi", "UAE")).build());
    Mockito.when(f1SchedulesClient.getNextRace()).thenReturn(nextRace);

    Optional<MessageContainer> resultMessageContainer = testObject.getNextRace();
    assertTrue(resultMessageContainer.isPresent());
    assertNotNull(resultMessageContainer.get().getPhoto());
    assertNotNull(resultMessageContainer.get().getText());
  }
}
