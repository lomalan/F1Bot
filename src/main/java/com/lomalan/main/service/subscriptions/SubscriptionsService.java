package com.lomalan.main.service.subscriptions;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface SubscriptionsService {

  Optional<String> subscribe(Update update);

  Optional<String> unsubscribe(Update update);

  String getCurrentCommand(Update update);

  boolean isSubscribed(Update update);
}
