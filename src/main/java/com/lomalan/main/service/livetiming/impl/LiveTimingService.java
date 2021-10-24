package com.lomalan.main.service.livetiming.impl;

import static com.lomalan.main.service.impl.MessageConstructor.constructMessage;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.livetiming.LiveTimingHtmlClient;
import com.lomalan.main.rest.client.livetiming.LiveTimingRestClient;
import com.lomalan.main.rest.model.livetiming.LiveTimingInfo;
import com.lomalan.main.rest.model.livetiming.StreamingStatus;
import com.lomalan.main.service.message.MessageExecutor;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LiveTimingService {

  private final TelegramUserRepository userRepository;
  private final MessageExecutor messageExecutor;
  private final LiveTimingRestClient liveTimingRestClient;
  private final LiveTimingHtmlClient liveTimingHtmlClient;


  @Scheduled(initialDelay = 10000, fixedDelay = 300000)
  public void getLiveDriverInfo() {
    log.info("Start to search live subs.....");
    StreamingStatus streamingStatus = liveTimingRestClient.getStreamingStatus();
    if (streamingStatus.getStatus().equals("Offline") || streamingStatus.getStatus().equals("Available")) {
      return;
    }
    Optional<LiveTimingInfo> liveTimingInfo = liveTimingHtmlClient.getLiveTimingInfo();
    liveTimingInfo
        .ifPresent(this::processMessage);
  }

  private void processMessage(LiveTimingInfo timingInfo) {
    String messageToExecute = constructMessage(timingInfo);
    log.info(messageToExecute);
    executeUpdatedData(messageToExecute);
  }

  private void executeUpdatedData(String message) {
    List<TelegramUser> telegramUsers = userRepository.findAll();
    telegramUsers.stream()
        .filter(TelegramUser::isSubscribedOnLiveUpdates)
        .forEach(user -> messageExecutor.executeMessage(user, message));
  }
}