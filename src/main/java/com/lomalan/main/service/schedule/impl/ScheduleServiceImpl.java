package com.lomalan.main.service.schedule.impl;

import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.service.impl.ImageProcessor;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final F1SchedulesClient client;

  public ScheduleServiceImpl(F1SchedulesClient client) {
    this.client = client;
  }

  @Override
  public Optional<MessageContainer> getNextRace() {
    Race nextRace = client.getNextRace();
    Optional<InputFile> photo = getPhoto(nextRace);
    String nextRaceMessage = MessageConstructor.constructNextRaceMessage(nextRace);
    return Optional.of(getMessageContainer(photo, nextRaceMessage));
  }

  private MessageContainer getMessageContainer(Optional<InputFile> photo, String nextRaceMessage) {
    return constructMessageContainer(nextRaceMessage, photo.orElse(null));
  }

  private MessageContainer constructMessageContainer(String text, InputFile photo) {
    return MessageContainer.builder().text(text).photo(photo).build();
  }

  private Optional<InputFile> getPhoto(Race nextRace) {
    if ("UAE".equals(nextRace.getCircuit().getLocation().getCountry())) {
      return ImageProcessor.getImage(nextRace.getCircuit().getLocation().getLocality());
    }
    return ImageProcessor.getImage(nextRace.getCircuit().getLocation().getCountry());
  }
}
