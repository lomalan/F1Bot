package com.lomalan.main.service.subscriptions.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.subscriptions.model.CommandNames;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class LiveTimingSubscriptionsServiceTest {
    private final TelegramUserRepository userRepository = mock(TelegramUserRepository.class);
    private final LiveTimingSubscriptionsService testObj = new LiveTimingSubscriptionsService(userRepository);

    @Test
    public void whenExecuteGetCommandNameThenReturnCommandName(){
        String expectedSubCommandName = "Subscribe on live timing";
        String expectedUnsubCommandName = "Unsubscribe from live timing";
        CommandNames result = testObj.getCommandName();

        assertEquals(expectedSubCommandName, result.getSubCommandName());
        assertEquals(expectedUnsubCommandName, result.getUnsubCommandName());
    }

    @Test
    public void whenExecuteSubUserThenReturnMessage() {
        String expectedMessage = "You're successfully subscribed on live updates.\n\n"
                +    "You will receive every 5 minutes race updates when it will start";
        TelegramUser telegramUser = TelegramUser.builder().build();
        assertEquals(expectedMessage, testObj.subUser(telegramUser));
        assertTrue(telegramUser.isSubscribedOnLiveUpdates());
    }

    @Test
    public void whenExecuteUnsubUserThenReturnMessage() {
        String expectedMessage = "You're successfully unsubscribed from live updates.";
        TelegramUser telegramUser = TelegramUser.builder().subscribedOnLiveUpdates(true).build();
        assertEquals(expectedMessage, testObj.unsubUser(telegramUser));
        assertFalse(telegramUser.isSubscribedOnLiveUpdates());
    }

    @Test
    public void whenExecuteIsUserSubscribedThenReturnBoolean() {
        assertTrue(testObj.isUserSubscribed(TelegramUser.builder().subscribedOnLiveUpdates(true).build()));
    }
}
