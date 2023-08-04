package com.lomalan.main.service.consumer;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.service.message.MessageExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class TwitterConsumer {

    private final MessageExecutor messageExecutor;
    private final TelegramUserRepository userRepository;

    @KafkaListener(topics = "${kafka.topic.resource-configuration}", autoStartup = "true")
    public void consume(String tweetUrl) {
        log.info(String.format("#### -> Consumed message -> %s", tweetUrl));
        userRepository.findAll().stream().filter(TelegramUser::isSubscribedOnTwitterUpdates)
                .forEach(user -> messageExecutor.executeMessage(user, tweetUrl));
    }
}
