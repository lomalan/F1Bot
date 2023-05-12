package com.lomalan.main.service.livetiming.impl;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.client.livetiming.LiveTimingClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.service.message.MessageExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Processing live time real data during the race
 */
@Service
@AllArgsConstructor
@Slf4j
public class LiveTimingService {

  private final TelegramUserRepository userRepository;
  private final MessageExecutor messageExecutor;
  private final LiveTimingClient liveTimingClient;
  private final F1SchedulesClient f1SchedulesRestClient;

  @Scheduled(cron = "0 0/5 * * * SAT-SUN")
  public void getLiveDriverInfo() {
    Race nextRace = f1SchedulesRestClient.getNextRace();

    if (isInvalidDateForLiveTiming(nextRace)) {
      return;
    }
    log.info("Start to search live subs.....");
    executeDataForSubscribers();
  }

  private boolean isInvalidDateForLiveTiming(Race nextRace) {
    return nextRace.isInvalidDateForLiveTimingPosting()
        && nextRace.getQuali().isInvalidDateForLiveTimingPosting()
        && (nextRace.getSprint() == null || nextRace.getSprint().isInvalidDateForLiveTimingPosting());
  }

  private void executeDataForSubscribers() {
    List<TelegramUser> allUsers = userRepository.findAll();
    allUsers.stream()
        .filter(TelegramUser::isSubscribedOnLiveUpdates)
        .forEach(this::executeMessage);
  }

  private void executeMessage(TelegramUser user) {
    liveTimingClient.getRaceLiveTiming()
        .ifPresent(raceLiveTiming -> messageExecutor.executeMessage(user, raceLiveTiming));
  }
}