package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.BotFacadeService;
import com.lomalan.main.service.MessageService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Service
@Slf4j
@AllArgsConstructor
public class BotFacadeServiceImpl implements BotFacadeService {

  private final List<MessageService> messageServices;

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    return sendBotApiMethod(update, processMessageServices(update)
                .orElse(new MessageContainer("Please use the main menu")));
  }
  
  private Optional<MessageContainer> processMessageServices(Update update) {
    return messageServices.stream()
        .map(messageService -> messageService.processMessage(update))
        .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty))
        .findFirst();
  }

  private PartialBotApiMethod<Message> sendBotApiMethod(Update update, MessageContainer container) {
    return container.getPhoto() != null
        ? sendPhoto(update, container)
        : sendMessage(update, container);
  }

  private SendMessage sendMessage(Update update, MessageContainer container) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setReplyMarkup(constructMarkup(update));
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText(container.getText());
    return sendMessage;
  }

  private SendPhoto sendPhoto(Update update, MessageContainer container) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setReplyMarkup(constructMarkup(update));
    sendPhoto.setChatId(update.getMessage().getChatId().toString());
    sendPhoto.setPhoto(container.getPhoto());
    sendPhoto.setCaption(container.getText());
    return sendPhoto;
  }

  private ReplyKeyboardMarkup constructMarkup(Update update) {
    List<String> listToFilterOut = messageServices.stream()
        .map(service -> service.getCurrentCommand(update))
        .collect(Collectors.toList());

    return KeyboardCreator
        .constructCustomMenuKeyBoard(getAvailableCommands(String.join(StringUtils.SPACE, listToFilterOut)));
  }

  private List<BotCommands> getAvailableCommands(String commandName) {
    return Arrays.stream(BotCommands.values()).filter(c -> !commandName.contains(c.getCommandName()))
        .collect(Collectors.toList());
  }
}
