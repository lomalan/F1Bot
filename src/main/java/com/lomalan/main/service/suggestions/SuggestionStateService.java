package com.lomalan.main.service.suggestions;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.Suggestion;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.SuggestionRepository;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.MessageService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class SuggestionStateService implements MessageService {


  private static final String SUGGESTIONS_LIMIT_MESSAGE = "Suggestions cannot be more than 5 per day per user";
  private static final String MESSAGE = "Thank you for your suggestion!";

  private SuggestionRepository suggestionRepository;
  private TelegramUserRepository userRepository;

  public Optional<MessageContainer> processMessage(Update update, TelegramUser user) {
    if (!user.getState().equals(BotState.SUGGESTION)) {
      return Optional.empty();
    }
    processUserStatus(user);
    return processSuggestions(update, String.valueOf(update.getMessage().getChatId()));
  }

  private void processUserStatus(TelegramUser user) {
    user.setState(BotState.MAIN_MENU);
    userRepository.save(user);
  }

  private Optional<MessageContainer> processSuggestions(Update update, String chatId) {
    if (BotCommands.CANCEL.getCommandName().equals(update.getMessage().getText())) {
      return Optional.empty();
    }
    List<Suggestion> suggestions = suggestionRepository.findByChatId(chatId).stream()
        .filter(suggestion -> suggestion.getDateStamp().equals(LocalDate.now()))
        .collect(Collectors.toList());

    if (suggestions.size() >= 5) {
      return Optional.of(new MessageContainer(SUGGESTIONS_LIMIT_MESSAGE));
    }

    saveSuggestion(update, chatId);
    return Optional.of(new MessageContainer(MESSAGE));
  }

  private void saveSuggestion(Update update, String chatId) {
    try {
      suggestionRepository.save(prepareSuggestionToSave(update, chatId));
    } catch (DuplicateKeyException ignored) {

    }
  }

  private Suggestion prepareSuggestionToSave(Update update, String chatId) {
    return Suggestion.builder()
        .chatId(chatId)
        .suggestionText(update.getMessage().getText())
        .dateStamp(LocalDate.now())
        .build();
  }
}
