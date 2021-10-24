package com.lomalan.main.service.impl;

import com.lomalan.main.service.BotFacadeService;
import com.lomalan.main.service.RaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@Slf4j
public class BotFacadeServiceImpl implements BotFacadeService {

  private final RaceService raceService;

  public BotFacadeServiceImpl(RaceService raceService) {
    this.raceService = raceService;
  }

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    return raceService.getRaceData(update)
        .orElse(sendDefaultMessage(update));
  }

  private SendMessage sendDefaultMessage(Update update) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setReplyMarkup(KeyboardCreator.constructMainMenuKeyboard());
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText("Please use the main menu.");
    return sendMessage;
  }
}
