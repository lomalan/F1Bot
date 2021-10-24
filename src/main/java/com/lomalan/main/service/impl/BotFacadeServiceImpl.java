package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.service.BotFacadeService;
import com.lomalan.main.service.RaceService;
import com.lomalan.main.service.subscriptions.SubscriptionsService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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

  private final RaceService raceService;
  private final List<SubscriptionsService> subscriptionsServices;

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    return raceService.getRaceData(update)
        .orElse(sendDefaultMessage(update, processSubscriptionServices(update)
            .orElse("Please use the main menu")));
  }

  private SendMessage sendDefaultMessage(Update update, java.lang.String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setReplyMarkup(constructMarkup(update));
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText(text);
    return sendMessage;
  }

  private Optional<String> processSubscriptionServices(Update update) {
    return subscriptionsServices.stream()
        .map(subService -> subService.subscribe(update).orElse(subService.unsubscribe(update).orElse(null)))
        .filter(Objects::nonNull)
        .findFirst();
  }

  private ReplyKeyboardMarkup constructMarkup(Update update) {
    List<String> listToFilterOut = subscriptionsServices.stream()
        .map(service -> service.getCurrentCommand(update))
        .collect(Collectors.toList());

    return KeyboardCreator
        .constructCustomMenuKeyBoard(getAvailableCommands(java.lang.String.join(StringUtils.SPACE, listToFilterOut)));
  }

  private List<BotCommands> getAvailableCommands(java.lang.String commandName) {
    return Arrays.stream(BotCommands.values()).filter(c -> !commandName.contains(c.getCommandName()))
        .collect(Collectors.toList());
  }
}
