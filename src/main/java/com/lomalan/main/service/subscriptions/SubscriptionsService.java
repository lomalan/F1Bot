package com.lomalan.main.service.subscriptions;

import com.lomalan.main.dao.model.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Using for subscribe/unsubscribe users to content
 */
public interface SubscriptionsService {

  String subscribe(Update update, TelegramUser user);

  String unsubscribe(Update update, TelegramUser user);
}
