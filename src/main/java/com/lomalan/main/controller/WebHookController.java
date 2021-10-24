package com.lomalan.main.controller;

import com.lomalan.main.bot.SportNewsBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

  private final SportNewsBot sportNewsBot;

  public WebHookController(SportNewsBot sportNewsBot) {
    this.sportNewsBot = sportNewsBot;
  }

  @PostMapping(value = "/")
  public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
    return sportNewsBot.onWebhookUpdateReceived(update);
  }

}
