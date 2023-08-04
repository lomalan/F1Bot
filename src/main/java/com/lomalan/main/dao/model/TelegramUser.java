package com.lomalan.main.dao.model;

import com.lomalan.main.bot.state.BotState;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
public class TelegramUser {

    @Id
    private String id;
    private Boolean isBot;
    private String firstName;
    private String lastName;
    private String userName;

    @Indexed(unique = true)
    private String chatId;

    private boolean subscribedOnWeather;
    private boolean subscribedOnLiveUpdates;
    private boolean subscribedOnTwitterUpdates;
    private BotState state;
}
