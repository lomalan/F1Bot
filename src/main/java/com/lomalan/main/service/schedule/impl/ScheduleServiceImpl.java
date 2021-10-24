package com.lomalan.main.service.schedule.impl;

import com.lomalan.main.rest.client.f1.F1SchedulesRestClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.service.impl.ImageProcessor;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.schedule.ScheduleService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final F1SchedulesRestClient client;

  public ScheduleServiceImpl(F1SchedulesRestClient client) {
    this.client = client;
  }

  @Override
  public SendPhoto getNextRace(Update update) {
    Race nextRace = client.getNextRace();
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(update.getMessage().getChatId().toString());
    sendPhoto.setPhoto(ImageProcessor.getImage(nextRace.getCircuit().getLocation().getCountry()));
    sendPhoto.setCaption(MessageConstructor.constructNextRaceMessage(nextRace));
    return sendPhoto;
  }

}
