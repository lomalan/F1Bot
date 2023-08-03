package com.lomalan.main.service.impl;

import static com.lomalan.main.service.subscriptions.util.TelegramUserConstructor.prepareUserToSave;

import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.BotFacadeService;
import com.lomalan.main.service.BotMenuService;
import com.lomalan.main.service.MessageService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@AllArgsConstructor
public class BotFacadeServiceImpl implements BotFacadeService {

  private final List<MessageService> messageServices;
  private final TelegramUserRepository userRepository;
  private final BotMenuService menuService;

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    String chatId = update.getMessage().getChatId().toString();
    TelegramUser telegramUser = processUserFromUpdate(update, chatId);
    return sendBotApiMethod(update, getMessageIfNotEmpty(processMessageServices(update, telegramUser)), telegramUser);
  }

  private MessageContainer getMessageIfNotEmpty(Optional<MessageContainer> optionalMessageContainer) {
    return optionalMessageContainer
        .orElse(new MessageContainer("Please use the main menu"));
  }

  private TelegramUser processUserFromUpdate(Update update, String chatId) {
    TelegramUser user = userRepository.findByChatId(chatId);
    if (user == null) {
      return userRepository.save(prepareUserToSave(update.getMessage().getFrom(), BotState.MAIN_MENU));
    }
    return user;
  }

  private Optional<MessageContainer> processMessageServices(Update update, TelegramUser user) {
    return messageServices.stream()
        .map(messageService -> messageService.processMessage(update, user))
        .flatMap(Optional::stream)
        .findFirst();
  }

  private PartialBotApiMethod<Message> sendBotApiMethod(Update update, MessageContainer container, TelegramUser user) {
    return container.getPhoto() != null
        ? sendPhoto(update, container, user)
        : sendMessage(update, container, user);
  }

  private SendMessage sendMessage(Update update, MessageContainer container, TelegramUser user) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setReplyMarkup(menuService.constructMenu(update, user));
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText(container.getText());
    return sendMessage;
  }

  private SendPhoto sendPhoto(Update update, MessageContainer container, TelegramUser user) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setReplyMarkup(menuService.constructMenu(update, user));
    sendPhoto.setChatId(update.getMessage().getChatId().toString());
    sendPhoto.setPhoto(container.getPhoto());
    sendPhoto.setCaption(container.getText());
    return sendPhoto;
  }
}
