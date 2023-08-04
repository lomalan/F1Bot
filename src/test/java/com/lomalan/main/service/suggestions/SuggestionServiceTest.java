package com.lomalan.main.service.suggestions;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SuggestionServiceTest {

    private final TelegramUserRepository telegramUserRepository = mock(TelegramUserRepository.class);
    private final SuggestionService suggestionService = new SuggestionService(telegramUserRepository);

    @Test
    void whenCommandIsNotSuggestionThenReturnEmpty() {

        Optional<MessageContainer> messageContainer = suggestionService.processMessage(createUpdate("not suggestion"),
                null);

        assertTrue(messageContainer.isEmpty());
    }

    @Test
    void whenCommandIsSuggestionThenReturnMessageAndChangeUserState() {

        Optional<MessageContainer> messageContainer = suggestionService
                .processMessage(createUpdate(BotCommands.SUGGESTION.getCommandName()), TelegramUser.builder().build());

        assertTrue(messageContainer.isPresent());
        assertEquals("Please write your suggestions. If you changed your mind, press 'Cancel'",
                messageContainer.get().getText());
    }
}
