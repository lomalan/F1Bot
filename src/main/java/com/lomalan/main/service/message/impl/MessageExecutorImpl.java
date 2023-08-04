package com.lomalan.main.service.message.impl;

import com.lomalan.main.bot.SportNewsBot;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.service.message.MessageExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
@Slf4j
public class MessageExecutorImpl implements MessageExecutor {

    private final SportNewsBot sportNewsBot;

    @Override
    public void executeMessage(TelegramUser user, String messageText) {
        SendMessage sendMessage = SendMessage.builder().chatId(user.getChatId()).text(messageText).build();
        try {
            sportNewsBot.executeMethod(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
