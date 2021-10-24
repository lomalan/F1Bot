package com.lomalan.main.service.subscriptions;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Using for subscribe/unsubscribe users to content
 */
public interface SubscriptionsService {

  String subscribe(Update update);

  String unsubscribe(Update update);

  boolean isSubscribed(Update update);
}
