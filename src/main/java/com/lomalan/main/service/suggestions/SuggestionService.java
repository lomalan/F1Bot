package com.lomalan.main.service.suggestions;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.MessageService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class SuggestionService implements MessageService {

  private static final String MESSAGE = "Please write your suggestions. If you changed your mind, press 'Cancel'";
  private TelegramUserRepository userRepository;

  @Override
  public Optional<MessageContainer> processMessage(Update update, TelegramUser user) {
    if (!BotCommands.SUGGESTION.getCommandName().equals(update.getMessage().getText())) {
      return Optional.empty();
    }
    saveUser(user);
    return Optional.of(new MessageContainer(MESSAGE));
  }

  private void saveUser(TelegramUser telegramUser) {
    telegramUser.setState(BotState.SUGGESTION);
    userRepository.save(telegramUser);
  }
}
