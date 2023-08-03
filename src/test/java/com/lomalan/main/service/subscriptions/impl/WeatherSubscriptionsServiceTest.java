package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Optional;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WeatherSubscriptionsServiceTest {
    private final TelegramUserRepository telegramUserRepository = mock(TelegramUserRepository.class);
    private final AbstractSubscriptionsService testObj = new WeatherSubscriptionsService(telegramUserRepository);

    @Test
    public void whenSubsAndWantSubsThenReturnMessageSubs() {
        // given
        TelegramUser telegramUser = TelegramUser.builder().subscribedOnWeather(true).build();
        Update update = createUpdate("Subscribe on weather");
        // when
        Optional<MessageContainer> messageContainer = testObj.processMessage(update, telegramUser);
        // then
        assertEquals("You're already subscribed", messageContainer.get().getText());
        verify(telegramUserRepository, times(0)).save(telegramUser);
    }

    @Test
    public void whenUnsubWantUnsubThenReturnMessageUnsub() {
        //given
        TelegramUser telegramUser = TelegramUser.builder().subscribedOnWeather(false).build();
        Update update = createUpdate("Unsubscribe from weather");
        // when
        Optional<MessageContainer> messageContainer = testObj.processMessage(update, telegramUser);
        // then
        assertEquals("You're already unsubscribed", messageContainer.get().getText());
        verify(telegramUserRepository, times(0)).save(telegramUser);
    }

    @Test
    public void whenUnsubWantSubThenSubAndReturnMessageSub() {
        // given
        TelegramUser telegramUser = TelegramUser.builder().userName("name").subscribedOnWeather(false).build();
        Update update = createUpdate("Subscribe on weather");

        String expectedMessage = "You're successfully subscribed on weather updates.\n\n"
                + "You will receive every 30 minutes weather updates during the weekend before qualification and race";
        // when
        Optional<MessageContainer> messageContainer = testObj.processMessage(update, telegramUser);
        // then
        assertEquals(expectedMessage, messageContainer.get().getText());
        assertTrue(telegramUser.isSubscribedOnWeather());
        verify(telegramUserRepository, times(1)).save(telegramUser);
    }

    @Test
    public void whenSubWantUnsubThenUnsubAndReturnMessageUnsub() {
        // given
        TelegramUser telegramUser = TelegramUser.builder().subscribedOnWeather(true).build();
        Update update = createUpdate("Unsubscribe from weather");
        String expectedMessage = "You're successfully unsubscribed from weather updates.";
        // when
        Optional<MessageContainer> messageContainer = testObj.processMessage(update, telegramUser);
        // then
        assertEquals(expectedMessage, messageContainer.get().getText());
        assertFalse(telegramUser.isSubscribedOnWeather());
        verify(telegramUserRepository, times(1)).save(telegramUser);
    }
}
