package com.lomalan.main.service.impl;

import static com.lomalan.main.service.impl.KeyboardCreator.constructCustomMenuKeyBoard;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.service.BotMenuService;
import com.lomalan.main.service.MessageService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@AllArgsConstructor
public class MenuService implements BotMenuService {

    private final List<MessageService> messageServices;

    @Override
    public ReplyKeyboardMarkup constructMenu(Update update, TelegramUser user) {
        if (BotCommands.SUGGESTION.getCommandName().equals(update.getMessage().getText())) {
            return constructCustomMenuKeyBoard(Collections.singletonList(BotCommands.CANCEL));
        }
        List<String> listToFilterOut = messageServices.stream().map(service -> service.getCurrentCommand(user))
                .collect(Collectors.toList());

        return constructCustomMenuKeyBoard(getAvailableCommands(String.join(StringUtils.SPACE, listToFilterOut)));
    }

    private List<BotCommands> getAvailableCommands(String commandName) {
        return Arrays.stream(BotCommands.values()).filter(c -> !commandName.contains(c.getCommandName()))
                .filter(command -> BotCommands.CANCEL != command).collect(Collectors.toList());
    }
}
