package com.lomalan.main.service.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.BotFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;


@Service
@Slf4j
public class BotFacadeServiceImpl implements BotFacadeService {

  private final TelegramUserRepository telegramUserRepository;

  public BotFacadeServiceImpl(TelegramUserRepository telegramUserRepository) {
    this.telegramUserRepository = telegramUserRepository;
  }

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      processUser(update);
      SendMessage message = new SendMessage();
      message.setChatId(update.getMessage().getChatId().toString());
      message.setText(text);
      return message;
    }
    SendSticker sticker = new SendSticker();
    sticker.setChatId(update.getMessage().getChatId().toString());
    sticker.setSticker(new InputFile(update.getMessage().getSticker().getFileId()));
    return sticker;
  }

  private void processUser(Update update) {
    Message message = update.getMessage();
    User user = message.getFrom();
    if (telegramUserRepository.findByChatId(String.valueOf(user.getId())) == null) {
      TelegramUser telegramUser = TelegramUser.builder()
          .userName(user.getUserName())
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .chatId(String.valueOf(user.getId()))
          .isBot(user.getIsBot())
          .build();
      telegramUserRepository.save(telegramUser);
    }
  }
}
