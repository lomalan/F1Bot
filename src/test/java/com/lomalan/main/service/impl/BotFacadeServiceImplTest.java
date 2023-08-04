package com.lomalan.main.service.impl;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.BotMenuService;
import com.lomalan.main.service.MessageService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

class BotFacadeServiceImplTest {

    private final MessageService messageServices = mock(MessageService.class);
    private final TelegramUserRepository userRepository = mock(TelegramUserRepository.class);
    private final BotMenuService botMenuService = mock(BotMenuService.class);

    private final BotFacadeServiceImpl botFacadeService = new BotFacadeServiceImpl(List.of(messageServices),
            userRepository, botMenuService);

    private static final String RANDOM_TEXT = "Random text";
    private static final String MAIN_MENU_TEXT = "Please use the main menu";

    @Test
    void whenUpdateIsEmptyForANewMemberThenReturnDefaultCommandAndSaveUser() {
        Update randomUpdate = createUpdate(RANDOM_TEXT);
        randomUpdate.getMessage().setFrom(new User());

        SendMessage result = (SendMessage) botFacadeService.processUpdateWithMessage(randomUpdate);

        assertEquals(MAIN_MENU_TEXT, result.getText());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateIsEmptyForAnOldMemberThenReturnDefaultCommandAndNotSaveUser() {
        Update randomUpdate = createUpdate(RANDOM_TEXT);
        randomUpdate.getMessage().setFrom(new User());
        when(userRepository.findByChatId(any())).thenReturn(TelegramUser.builder().build());

        SendMessage result = (SendMessage) botFacadeService.processUpdateWithMessage(randomUpdate);

        assertEquals(MAIN_MENU_TEXT, result.getText());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void whenUpdateIsNotEmptyForAnOldMemberThenReturnSendMessageResponse() {
        Update update = createUpdate(BotCommands.RACE_RESULTS.getCommandName());
        update.getMessage().setFrom(new User());
        TelegramUser telegramUser = TelegramUser.builder().build();
        when(userRepository.findByChatId(any())).thenReturn(telegramUser);
        String expectedMessage = "Race results are fine";
        when(messageServices.processMessage(update, telegramUser))
                .thenReturn(Optional.of(MessageContainer.builder().text(expectedMessage).build()));

        SendMessage result = (SendMessage) botFacadeService.processUpdateWithMessage(update);

        assertEquals(expectedMessage, result.getText());
        assertNull(result.getReplyMarkup());
        assertEquals("1", result.getChatId());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void whenUpdateIsNotEmptyAndGraphicalForAnOldMemberThenReturnSendPhotoResponse() {
        Update update = createUpdate(BotCommands.RACE_RESULTS.getCommandName());
        update.getMessage().setFrom(new User());
        TelegramUser telegramUser = TelegramUser.builder().build();
        when(userRepository.findByChatId(any())).thenReturn(telegramUser);
        String expectedMessage = "Race results are fine";
        InputFile expectedInputFile = mock(InputFile.class);
        when(messageServices.processMessage(update, telegramUser)).thenReturn(
                Optional.of(MessageContainer.builder().text(expectedMessage).photo(expectedInputFile).build()));

        SendPhoto result = (SendPhoto) botFacadeService.processUpdateWithMessage(update);

        assertEquals(expectedMessage, result.getCaption());
        assertEquals(expectedInputFile, result.getPhoto());
        assertNull(result.getReplyMarkup());
        assertEquals("1", result.getChatId());
        verify(userRepository, times(0)).save(any());
    }
}
