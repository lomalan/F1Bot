package com.lomalan.main.service.subscriptions.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.model.CommandNames;
import org.junit.jupiter.api.Test;

public class TwitterSubscriptionServiceTest {
    private final TelegramUserRepository userRepository = mock(TelegramUserRepository.class);
    private final AbstractSubscriptionsService testObj = new TwitterSubscriptionService(userRepository);

    @Test
    public void whenExecuteGetCommandNameThenReturnCommandName() {
        String subCommandName = "Subscribe on twitter updates";
        String unsubCommandName = "Unsubscribe from twitter updates";
        CommandNames result = testObj.getCommandName();
        assertEquals(subCommandName, result.getSubCommandName());
        assertEquals(unsubCommandName, result.getUnsubCommandName());
    }

    @Test
    public void whenSubUserThenReturnMessageSubUser() {
        String expectedMessage = "You're successfully subscribed on twitter updates of f1 official page.";
        TelegramUser telegramUser = TelegramUser.builder().build();
        assertEquals(expectedMessage, testObj.subUser(telegramUser));
        assertTrue(telegramUser.isSubscribedOnTwitterUpdates());
    }

    @Test
    public void whenUnsubUserThenReturnMessageUnsubUser() {
        String expectedMessage = "You're successfully unsubscribed from twitter updates.";
        TelegramUser telegramUser = TelegramUser.builder().build();
        assertEquals(expectedMessage, testObj.unsubUser(telegramUser));
        assertFalse(telegramUser.isSubscribedOnTwitterUpdates());
    }

    @Test
    public void whenExecuteIsUserSubscribedThenReturnTrue() {
        assertTrue(testObj.isUserSubscribed(TelegramUser.builder().subscribedOnTwitterUpdates(true).build()));
    }
}
