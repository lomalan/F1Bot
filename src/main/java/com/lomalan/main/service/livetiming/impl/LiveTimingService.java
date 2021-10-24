package com.lomalan.main.service.livetiming.impl;

import static com.lomalan.main.service.impl.MessageConstructor.constructMessage;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.livetiming.LiveTimingHtmlClient;
import com.lomalan.main.rest.client.livetiming.LiveTimingRestClient;
import com.lomalan.main.rest.model.livetiming.LiveTimingInfo;
import com.lomalan.main.rest.model.livetiming.SessionInfo;
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



  @Scheduled(cron = "0 0/1 * * * SAT-SUN")
  public void getLiveDriverInfo() {
    log.info("Start to search live subs.....");
    List<TelegramUser> telegramUsers = userRepository.findAll();
    if (telegramUsers.isEmpty()) {
      return;
    }
    SessionInfo sessionInfo = liveTimingRestClient.getCurrentSessionInfo();
    if (!sessionInfo.getStatus().equals("Complete") || !sessionInfo.getType().equals("Practice")) {
      return;
    }
    Optional<LiveTimingInfo> liveTimingInfo = liveTimingHtmlClient.getLiveTimingInfo();
    liveTimingInfo
        .ifPresent(timingInfo -> processMessage(timingInfo, telegramUsers));
  }

  private void processMessage(LiveTimingInfo timingInfo, List<TelegramUser> users) {
    String messageToExecute = constructMessage(timingInfo);
    log.info(messageToExecute);
    executeUpdatedData(users, messageToExecute);
  }

  private void executeUpdatedData(List<TelegramUser> telegramUsers, String message) {
    telegramUsers.stream()
        .filter(TelegramUser::isSubscribedOnLiveUpdates)
        .forEach(user -> messageExecutor.executeMessage(user, message));
  }
}