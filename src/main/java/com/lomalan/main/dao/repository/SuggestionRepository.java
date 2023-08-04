package com.lomalan.main.dao.repository;

import com.lomalan.main.dao.model.Suggestion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuggestionRepository extends MongoRepository<Suggestion, UUID> {

    List<Suggestion> findByChatId(String chatId);
}
