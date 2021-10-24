package com.lomalan.main.service.impl;

import com.lomalan.main.bot.commands.BotCommands;
import java.util.List;
import java.util.stream.Collectors;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class KeyboardCreator {

  private KeyboardCreator() {}

  public static ReplyKeyboardMarkup constructCustomMenuKeyBoard(List<BotCommands> commands) {
    List<KeyboardRow> customRows = commands.stream().map(KeyboardCreator::createKeyboardRow)
        .collect(Collectors.toList());
    return getReplyKeyboardMarkup(customRows);
  }

  private static ReplyKeyboardMarkup getReplyKeyboardMarkup(List<KeyboardRow> keyboardRows) {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(false);
    replyKeyboardMarkup.setKeyboard(keyboardRows);
    return replyKeyboardMarkup;
  }

  private static KeyboardRow createKeyboardRow(BotCommands command) {
    KeyboardRow row = new KeyboardRow();
    row.add(new KeyboardButton(command.getCommandName()));
    return row;
  }
}
