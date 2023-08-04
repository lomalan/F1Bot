package com.lomalan.main.service.message.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lomalan.main.bot.SportNewsBot;
import com.lomalan.main.dao.model.TelegramUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageExecutorImplTest {

    private final SportNewsBot sportNewsBot = mock(SportNewsBot.class);
    private final MessageExecutorImpl messageExecutor = new MessageExecutorImpl(sportNewsBot);

    @Test
    public void WhenCollingMethodExecutesSuccessThenExecuteSendMessage() throws TelegramApiException {
        messageExecutor.executeMessage(TelegramUser.builder().chatId("ik").build(), "Success");
        SendMessage expectedSendMessage = SendMessage.builder().chatId("ik").text("Success").build();
        verify(sportNewsBot, times(1)).executeMethod(expectedSendMessage);
    }

    @Test
    public void whenSportsNewsBotThrowingExceptionThenThrowIllegalArgumentException() throws TelegramApiException {
        String expectedExceptionMessage = "Telegram is broken";
        TelegramApiException e = new TelegramApiException(expectedExceptionMessage);
        SendMessage expectedSendMessage = SendMessage.builder().chatId("id").text("Success").build();

        Mockito.doThrow(e).when(sportNewsBot).executeMethod(expectedSendMessage);
        IllegalArgumentException resultedException = assertThrows(IllegalArgumentException.class,
                () -> messageExecutor.executeMessage(TelegramUser.builder().chatId("id").build(), "Success"));
        assertEquals(expectedExceptionMessage, resultedException.getMessage());
    }
}
