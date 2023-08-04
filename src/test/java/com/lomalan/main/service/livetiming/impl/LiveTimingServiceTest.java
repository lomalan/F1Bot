package com.lomalan.main.service.livetiming.impl;

import static com.lomalan.main.TestResources.createNextRace;
import static org.mockito.ArgumentMatchers.any;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.client.livetiming.LiveTimingClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.service.message.MessageExecutor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LiveTimingServiceTest {
    private TelegramUserRepository userRepository;
    private MessageExecutor messageExecutor;
    private LiveTimingClient liveTimingClient;
    private F1SchedulesClient f1SchedulesRestClient;
    private LiveTimingService testObject;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(TelegramUserRepository.class);
        messageExecutor = Mockito.mock(MessageExecutor.class);
        liveTimingClient = Mockito.mock(LiveTimingClient.class);
        f1SchedulesRestClient = Mockito.mock(F1SchedulesClient.class);
        testObject = new LiveTimingService(userRepository, messageExecutor, liveTimingClient, f1SchedulesRestClient);
    }

    @Test
    public void whenThereAreInvalidDateForLiveTimingThenDoNothing() {
        Race race = createNextRace(LocalDateTime.now().plusHours(1));
        Mockito.when(f1SchedulesRestClient.getNextRace()).thenReturn(race);

        testObject.getLiveDriverInfo();

        Mockito.verify(f1SchedulesRestClient, Mockito.times(1)).getNextRace();
        Mockito.verify(userRepository, Mockito.times(0)).findAll();
        Mockito.verify(liveTimingClient, Mockito.times(0)).getRaceLiveTiming();
        Mockito.verify(messageExecutor, Mockito.times(0)).executeMessage(any(), any());
    }

    @Test
    public void whenThereAreValidDateButThereIsNotAnySubsOnLiveTimingThenDoNothing() {
        Race race = createNextRace(LocalDateTime.now().minusHours(1));
        Mockito.when(f1SchedulesRestClient.getNextRace()).thenReturn(race);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(TelegramUser.builder().id("id").build()));

        testObject.getLiveDriverInfo();

        Mockito.verify(f1SchedulesRestClient, Mockito.times(1)).getNextRace();
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(liveTimingClient, Mockito.times(0)).getRaceLiveTiming();
        Mockito.verify(messageExecutor, Mockito.times(0)).executeMessage(any(), any());
    }

    @Test
    public void whenThereAreValidDateAndThereAreSubsOnLiveTimingButNoLifeTimingDataThenDoNothing() {
        Race race = createNextRace(LocalDateTime.now().minusHours(1));
        Mockito.when(f1SchedulesRestClient.getNextRace()).thenReturn(race);
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(TelegramUser.builder().subscribedOnLiveUpdates(true).build()));
        Mockito.when((liveTimingClient.getRaceLiveTiming())).thenReturn(Optional.empty());

        testObject.getLiveDriverInfo();

        Mockito.verify(f1SchedulesRestClient, Mockito.times(1)).getNextRace();
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(liveTimingClient, Mockito.times(1)).getRaceLiveTiming();
        Mockito.verify(messageExecutor, Mockito.times(0)).executeMessage(any(), any());
    }

    @Test
    public void whenValidDataAndThereAreSubsAndLiveTimingDataIsPresentThenExecuteLiveTimingMessage() {
        Race race = createNextRace(LocalDateTime.now().minusHours(1));
        Mockito.when(f1SchedulesRestClient.getNextRace()).thenReturn(race);
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(TelegramUser.builder().subscribedOnLiveUpdates(true).build()));
        Mockito.when((liveTimingClient.getRaceLiveTiming())).thenReturn(Optional.of("LiveTimingResults"));

        testObject.getLiveDriverInfo();

        Mockito.verify(f1SchedulesRestClient, Mockito.times(1)).getNextRace();
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(liveTimingClient, Mockito.times(1)).getRaceLiveTiming();
        Mockito.verify(messageExecutor, Mockito.times(1))
                .executeMessage(TelegramUser.builder().subscribedOnLiveUpdates(true).build(), "LiveTimingResults");
    }
}
