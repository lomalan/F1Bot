package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.service.BotFacadeService;
import com.lomalan.main.service.MessageService;
import com.lomalan.main.service.PartialBotMethodService;
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
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Service
@Slf4j
@AllArgsConstructor
public class BotFacadeServiceImpl implements BotFacadeService {

  private final List<PartialBotMethodService> botApiMethods;
  private final List<MessageService> messageServices;

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    return processBotApiMethods(update)
            .orElse(sendMessage(update, processMessageServices(update)
                .orElse("Please use the main menu")));
  }

  private Optional<PartialBotApiMethod<Message>> processBotApiMethods(Update update) {
    return botApiMethods.stream().map(s -> s.getPartialBotApiMethod(update))
        .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty))
        .findFirst();
  }

  private Optional<String> processMessageServices(Update update) {
    return messageServices.stream()
        .map(messageService -> messageService.processMessage(update))
        .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty))
        .findFirst();
  }

  private SendMessage sendMessage(Update update, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setReplyMarkup(constructMarkup(update));
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText(text);
    return sendMessage;
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
