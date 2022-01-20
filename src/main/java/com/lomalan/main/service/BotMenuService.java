package com.lomalan.main.service;

import com.lomalan.main.dao.model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * Using for handling bot menu
 */
public interface BotMenuService {

  ReplyKeyboardMarkup constructMenu(Update update, TelegramUser user);
}
