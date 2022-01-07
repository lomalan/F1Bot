package com.lomalan.main.service.schedule.impl;

import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.service.impl.ImageProcessor;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final F1SchedulesClient client;

  public ScheduleServiceImpl(F1SchedulesClient client) {
    this.client = client;
  }

  @Override
  public PartialBotApiMethod<Message> getNextRace(Update update) {
    Race nextRace = client.getNextRace();
    Optional<InputFile> photo = getPhoto(nextRace);
    if (photo.isPresent()) {
      return createMessageWithPhoto(update, nextRace, photo.get());
    }
    return createMessage(update, nextRace);
  }

  private Optional<InputFile> getPhoto(Race nextRace) {
    if (nextRace.getCircuit().getLocation().getCountry().equals("UAE")) {
      return ImageProcessor.getImage(nextRace.getCircuit().getLocation().getLocality());
    }
    return ImageProcessor.getImage(nextRace.getCircuit().getLocation().getCountry());
  }

  private SendPhoto createMessageWithPhoto(Update update, Race nextRace, InputFile photo) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(update.getMessage().getChatId().toString());
    sendPhoto.setPhoto(photo);
    sendPhoto.setCaption(MessageConstructor.constructNextRaceMessage(nextRace));
    return sendPhoto;
  }

  private SendMessage createMessage(Update update, Race nextRace) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText(MessageConstructor.constructNextRaceMessage(nextRace));
    return sendMessage;
  }
}
