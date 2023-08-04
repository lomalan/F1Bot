package com.lomalan.main.dao.repository;

import com.lomalan.main.dao.model.TelegramUser;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TelegramUserRepository extends MongoRepository<TelegramUser, String> {

    List<TelegramUser> findByUserName(String userName);

    TelegramUser findByChatId(String chatId);
}
