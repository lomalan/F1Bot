package com.lomalan.main.service.suggestions;


import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.Suggestion;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.SuggestionRepository;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.lomalan.main.TestResources.createUpdate;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SuggestionStateServiceTest {

    private SuggestionRepository suggestionRepository;
    private TelegramUserRepository userRepository;

    private SuggestionStateService testObject;

    @BeforeEach
    void setUp() {
        suggestionRepository = mock(SuggestionRepository.class);
        userRepository = mock(TelegramUserRepository.class);

        testObject = new SuggestionStateService(suggestionRepository, userRepository);
    }

    @Test
    void whenStateIsNotSuggestionThenReturnEmptyResult() {
        Optional<MessageContainer> result = testObject.processMessage(
                createUpdate("Invalid"),
                TelegramUser.builder().state(BotState.MAIN_MENU).build()
        );
        assertTrue(result.isEmpty());

        verify(userRepository, times(0)).save(any());
        verify(suggestionRepository, times(0)).findByChatId(any());
        verify(suggestionRepository, times(0)).save(any());
    }

    @Test
    void whenCommandIsCancelThenReturnEmptyResult() {
        Optional<MessageContainer> result = testObject.processMessage(
                createUpdate(BotCommands.CANCEL.getCommandName()),
                TelegramUser.builder().state(BotState.SUGGESTION).build()
        );

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).save(any());
        verify(suggestionRepository, times(0)).findByChatId(any());
        verify(suggestionRepository, times(0)).save(any());
    }

    @Test
    void whenSuggestionInTextButMoreThan5ForMemberThenReturnWarningMessage() {
        when(suggestionRepository.findByChatId(any())).thenReturn(createSuggestions(5));
        Optional<MessageContainer> result = testObject.processMessage(
                createUpdate("Suggestion 6"),
                TelegramUser.builder().state(BotState.SUGGESTION).build()
        );

        assertTrue(result.isPresent());
        assertEquals("Suggestions cannot be more than 5 per day per user", result.get().getText());
        verify(userRepository, times(1)).save(any());
        verify(suggestionRepository, times(1)).findByChatId(any());
        verify(suggestionRepository, times(0)).save(any());
    }

    @Test
    void whenSuggestionInTextAndLessThan5ForMemberThenReturnSuccessMessage() {
        when(suggestionRepository.findByChatId(any())).thenReturn(createSuggestions(0));
        Optional<MessageContainer> result = testObject.processMessage(
                createUpdate("Suggestion 1"),
                TelegramUser.builder().state(BotState.SUGGESTION).build()
        );

        assertTrue(result.isPresent());
        assertEquals("Thank you for your suggestion!", result.get().getText());
        verify(userRepository, times(1)).save(any());
        verify(suggestionRepository, times(1)).findByChatId(any());
        verify(suggestionRepository, times(1)).save(any());
    }

    private List<Suggestion> createSuggestions(int count) {
        return IntStream.range(0, count)
                .mapToObj(s ->
                        Suggestion.builder()
                                .suggestionText("Suggestion " + s)
                                .dateStamp(LocalDate.now()).build()
                ).collect(Collectors.toList());
    }
}
