package com.lomalan.main.service.subscriptions.util;

import com.lomalan.main.bot.state.BotState;
import com.lomalan.main.dao.model.TelegramUser;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramUserConstructorTest {

   @Test
    public void whenCallingMethodThenReceivingResult(){
       User user = new User();
       user.setUserName("name");
       user.setFirstName("firstName");
       user.setLastName("lastName");
       user.setId(111L);
       user.setIsBot(false);
       BotState botState = BotState.MAIN_MENU;
       assertEquals(TelegramUser.builder()
               .userName("name")
               .firstName("firstName")
               .lastName("lastName")
               .chatId("111")
               .isBot(false).
               state(BotState.MAIN_MENU)
               .build(), TelegramUserConstructor.prepareUserToSave(user, botState));
   }
}
