package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.service.MessageService;
import com.lomalan.main.service.schedule.ScheduleService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class RaceService implements MessageService {

    private final ScheduleService scheduleService;

    @Override
    public Optional<MessageContainer> processMessage(Update update, TelegramUser user) {
        return getRaceData(update);
    }

    private Optional<MessageContainer> getRaceData(Update update) {
        if (BotCommands.NEXT_RACE.getCommandName().equals(update.getMessage().getText())) {
            return scheduleService.getNextRace();
        }
        return Optional.empty();
    }
}
