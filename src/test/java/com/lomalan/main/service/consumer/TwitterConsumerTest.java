package com.lomalan.main.service.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.message.MessageExecutor;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TwitterConsumerTest {

    private MessageExecutor messageExecutor;
    private TelegramUserRepository telegramUserRepository;
    private TwitterConsumer testObject;

    @BeforeEach
    void setUp() {
        messageExecutor = mock(MessageExecutor.class);
        telegramUserRepository = mock(TelegramUserRepository.class);
        testObject = new TwitterConsumer(messageExecutor, telegramUserRepository);
    }

  @Test
  void whenThereAreNoUsersSubscribeOnTwitterUpdatesThenNotExecuteMessages() {
    when(telegramUserRepository.findAll())
        .thenReturn(Collections.singletonList(TelegramUser.builder().build()));
    testObject.consume("url");

    Mockito.verify(telegramUserRepository, times(1)).findAll();
    Mockito.verify(messageExecutor, times(0)).executeMessage(any(), any());
  }

  @Test
  void whenThereAreUsersSubscribeOnTwitterUpdatesThenExecuteMessages() {
    when(telegramUserRepository.findAll())
        .thenReturn(
            Collections.singletonList(
                TelegramUser.builder().subscribedOnTwitterUpdates(true).build()));
    testObject.consume("url");

    Mockito.verify(telegramUserRepository, times(1)).findAll();
    Mockito.verify(messageExecutor, times(1)).executeMessage(any(), any());
  }
}
