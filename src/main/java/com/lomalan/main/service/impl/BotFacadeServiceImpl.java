package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.service.BotFacadeService;
import com.lomalan.main.service.RaceService;
import com.lomalan.main.service.subscriptions.SubscriptionsService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Service
@Slf4j
public class BotFacadeServiceImpl implements BotFacadeService {

  private final RaceService raceService;
  private final SubscriptionsService subscriptionsService;

  public BotFacadeServiceImpl(RaceService raceService,
      SubscriptionsService subscriptionsService) {
    this.raceService = raceService;
    this.subscriptionsService = subscriptionsService;
  }

  @Override
  public PartialBotApiMethod<Message> processUpdateWithMessage(Update update) {
    return raceService.getRaceData(update)
        .orElse(sendDefaultMessage(update, subscriptionsService.subscribe(update)
            .orElseGet(() -> (subscriptionsService.unsubscribe(update))
                .orElse("Please use the main menu"))));
  }

  private SendMessage sendDefaultMessage(Update update, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setReplyMarkup(constructMarkup(update));
    sendMessage.setChatId(update.getMessage().getChatId().toString());
    sendMessage.setText(text);
    return sendMessage;
  }

  private ReplyKeyboardMarkup constructMarkup(Update update) {
    if (subscriptionsService.isSubscribed(update)) {
      return KeyboardCreator.constructCustomMenuKeyBoard(getAvailableCommands(BotCommands.CURRENT_WEATHER.getCommandName()));
    }
    return KeyboardCreator.constructCustomMenuKeyBoard(getAvailableCommands(BotCommands.UNSUB_WEATHER.getCommandName()));
  }

  private List<BotCommands> getAvailableCommands(String commandName) {
    return Arrays.stream(BotCommands.values()).filter(c -> !c.getCommandName().equals(commandName))
        .collect(Collectors.toList());
  }
}
