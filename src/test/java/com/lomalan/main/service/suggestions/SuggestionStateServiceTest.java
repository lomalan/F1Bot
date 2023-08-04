package com.lomalan.main.service.suggestions;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.Suggestion;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.SuggestionRepository;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class SuggestionStateServiceTest {

    private final TelegramUserRepository telegramUserRepository = Mockito.mock(TelegramUserRepository.class);
    private final SuggestionRepository suggestionRepository = Mockito.mock(SuggestionRepository.class);
    private final SuggestionStateService suggestionStateService = new SuggestionStateService(suggestionRepository,
            telegramUserRepository);

    private final ArgumentCaptor<Suggestion> suggestionCaptor = ArgumentCaptor.forClass(Suggestion.class);

    @Test
    void whenStateIsNotSuggestionThenReturnEmptyResult() {
        TelegramUser telegramUser = TelegramUser.builder().state(BotState.MAIN_MENU).build();

        Optional<MessageContainer> messageContainer = suggestionStateService.processMessage(createUpdate(""),
                telegramUser);

        assertTrue(messageContainer.isEmpty());
    }

    @Test
    void whenStateIsSuggestionThenReturnResult() {
        TelegramUser telegramUser = TelegramUser.builder().state(BotState.SUGGESTION).build();

        Optional<MessageContainer> messageContainer = suggestionStateService.processMessage(createUpdate("Some text"),
                telegramUser);

        assertTrue(messageContainer.isPresent());
        assertEquals("Thank you for your suggestion!", messageContainer.get().getText());
        Mockito.verify(telegramUserRepository, Mockito.times(1))
                .save(TelegramUser.builder().state(BotState.MAIN_MENU).build());
        Mockito.verify(suggestionRepository, Mockito.times(1)).findByChatId("1");
        Mockito.verify(suggestionRepository, Mockito.times(1)).save(suggestionCaptor.capture());

        Suggestion suggestion = suggestionCaptor.getValue();
        assertEquals("Some text", suggestion.getSuggestionText());
        assertEquals("1", suggestion.getChatId());
    }

    @Test
    void whenCommandIsCancelThenReturnEmptyResultButChangeUserState() {
        TelegramUser telegramUser = TelegramUser.builder().state(BotState.SUGGESTION).build();

        Optional<MessageContainer> messageContainer = suggestionStateService.processMessage(createUpdate("Cancel"),
                telegramUser);

        assertFalse(messageContainer.isPresent());
        Mockito.verify(telegramUserRepository, Mockito.times(1))
                .save(TelegramUser.builder().state(BotState.MAIN_MENU).build());
    }

    @Test
    void whenThereAreMoreThan5SuggestionsPerDayThenReturnResultWithExceededLimitMessage() {
        TelegramUser telegramUser = TelegramUser.builder().state(BotState.SUGGESTION).build();
        Mockito.when(suggestionRepository.findByChatId("1"))
                .thenReturn(IntStream.range(0, 7).mapToObj(i -> buildEmptySuggestion()).collect(Collectors.toList()));
        Optional<MessageContainer> messageContainer = suggestionStateService
                .processMessage(createUpdate("Suggestion 7"), telegramUser);

        assertTrue(messageContainer.isPresent());
        assertEquals("Suggestions cannot be more than 5 per day per user", messageContainer.get().getText());
        Mockito.verify(telegramUserRepository, Mockito.times(1))
                .save(TelegramUser.builder().state(BotState.MAIN_MENU).build());
    }

    private static Suggestion buildEmptySuggestion() {
        return Suggestion.builder().dateStamp(LocalDate.now()).build();
    }
}
