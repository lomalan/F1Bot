package com.lomalan.main.service.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.util.Arrays;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class MenuServiceTest {

    MessageService messageService1 = mock(MessageService.class);
    MessageService messageService2 = mock(MessageService.class);
    MessageService messageService3 = mock(MessageService.class);
    private final MenuService testObj = new MenuService(Arrays.asList(messageService1, messageService2, messageService3));

    @Test
    public void whenCommandIsSuggestionThenConstructCancel() {
        String suggestion = "Suggestion to improve bot";
        Update update = createUpdate(suggestion);
        String resultCommand = "Cancel";
        ReplyKeyboardMarkup keyboardMarkup = testObj.constructMenu(update, TelegramUser.builder().build());
        assertTrue(keyboardMarkup.getKeyboard().get(0).contains(resultCommand));
    }

    @Test
    public void whenNoCommandsThenConstructMenu() {
        String emptyCommand = "";
        Update update = createUpdate(emptyCommand);
        TelegramUser telegramUser = TelegramUser.builder().build();

        Mockito.when(messageService1.getCurrentCommand(telegramUser)).thenReturn("Unsubscribe from weather");
        Mockito.when(messageService2.getCurrentCommand(telegramUser)).thenReturn("Unsubscribe from live timing");
        Mockito.when(messageService3.getCurrentCommand(telegramUser)).thenReturn("Unsubscribe from twitter updates");

        ReplyKeyboardMarkup keyboardMarkup = testObj.constructMenu(update, telegramUser);

        assertTrue(keyboardMarkup.getKeyboard().get(0).contains("Next Race"));
        assertTrue(keyboardMarkup.getKeyboard().get(3).contains("Subscribe on weather"));
        assertTrue(keyboardMarkup.getKeyboard().get(4).contains("Subscribe on live timing"));
        assertTrue(keyboardMarkup.getKeyboard().get(5).contains("Subscribe on twitter updates"));
    }
}
