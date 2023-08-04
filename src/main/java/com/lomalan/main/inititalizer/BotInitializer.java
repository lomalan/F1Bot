package com.lomalan.main.inititalizer;

import com.lomalan.main.bot.SportNewsBot;
import com.lomalan.main.rest.client.TelegramClient;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class BotInitializer {

    private final SportNewsBot bot;
    private final TelegramClient client;
    private static final String SECRET_PLACEHOLDER = "<secret>";

    public BotInitializer(SportNewsBot bot, TelegramClient client) {
        this.bot = bot;
        this.client = client;
    }

    @EventListener({ ContextRefreshedEvent.class })
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot, SetWebhook.builder().url(bot.getBotPath()).build());
            setUpWebHook();
        } catch (TelegramApiRequestException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private void setUpWebHook() {
        if (bot.getBotPath().equals(SECRET_PLACEHOLDER)) {
            return;
        }
        HttpEntity<String> entity = client.setWebHook();
        log.info(entity.getBody());
    }
}
