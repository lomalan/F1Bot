package com.lomalan.main.bot;

import com.lomalan.main.configuration.BotConfig;
import com.lomalan.main.service.BotFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class SportNewsBot extends TelegramWebhookBot {

  private final BotConfig config;
  private final BotFacadeService botFacadeService;

  public SportNewsBot(BotConfig config, BotFacadeService botFacadeService) {
    this.config = config;
    this.botFacadeService = botFacadeService;
  }

  @Override
  public String getBotUsername() {
    return config.getName();
  }

  @Override
  public String getBotToken() {
    return config.getToken();
  }

  @Override
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
      try {
        processUpdate(update);
      } catch (TelegramApiException e) {
        log.error(e.getMessage());
        e.printStackTrace();
      }
    return null;
  }

  private void processUpdate(Update update) throws TelegramApiException {
    PartialBotApiMethod<Message> method = botFacadeService.processUpdateWithMessage(update);
    if (method instanceof SendMessage) {
      execute((SendMessage) method);
    } else {
      execute((SendSticker) method);
    }
  }

  @Override
  public String getBotPath() {
    return config.getWebHookUrl();
  }

}
