package com.lomalan.main.service.weather.impl;

import static com.lomalan.main.TestResources.createLocation;
import static com.lomalan.main.TestResources.createNextRace;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.dao.repository.TelegramUserRepository;
import com.lomalan.main.rest.client.f1.F1SchedulesClient;
import com.lomalan.main.rest.client.weather.WeatherClient;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.weather.Weather;
import com.lomalan.main.rest.model.weather.WeatherInfo;
import com.lomalan.main.rest.model.weather.WeatherResponse;
import com.lomalan.main.service.message.MessageExecutor;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class WeatherServiceTest {

    private F1SchedulesClient f1SchedulesClient;
    private WeatherClient weatherClient;
    private TelegramUserRepository telegramUserRepository;
    private MessageExecutor messageExecutor;
    private WeatherService testObject;

    private final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    @BeforeEach
    void setup() {
        f1SchedulesClient = Mockito.mock(F1SchedulesClient.class);
        weatherClient = Mockito.mock(WeatherClient.class);
        telegramUserRepository = Mockito.mock(TelegramUserRepository.class);
        messageExecutor = Mockito.mock(MessageExecutor.class);

        testObject = new WeatherService(f1SchedulesClient, weatherClient, telegramUserRepository, messageExecutor);
    }

    @Test
    public void whenThereAreNoSubsDoNothing() {
        Mockito.when(telegramUserRepository.findAll()).thenReturn(Collections.emptyList());

        testObject.scheduleWeatherUpdates();

        Mockito.verify(telegramUserRepository, Mockito.times(1)).findAll();
        Mockito.verify(f1SchedulesClient, Mockito.times(0)).getNextRace();
        Mockito.verify(weatherClient, Mockito.times(0)).getWeatherOnRaceLocation(any());
        Mockito.verify(messageExecutor, Mockito.times(0)).executeMessage(any(), any());
    }

    @Test
    public void whenThereAreInvalidDateForWeatherPostingThenDoNothing() {
        Mockito.when(telegramUserRepository.findAll())
                .thenReturn(List.of(TelegramUser.builder().subscribedOnWeather(true).build()));

        Race race = createNextRace(LocalDateTime.now().plusHours(5));

        Mockito.when(f1SchedulesClient.getNextRace()).thenReturn(race);

        testObject.scheduleWeatherUpdates();

        Mockito.verify(telegramUserRepository, Mockito.times(1)).findAll();
        Mockito.verify(f1SchedulesClient, Mockito.times(1)).getNextRace();
        Mockito.verify(weatherClient, Mockito.times(0)).getWeatherOnRaceLocation(any());
        Mockito.verify(messageExecutor, Mockito.times(0)).executeMessage(any(), any());
    }

    @Test
    public void whenThereAreValidDateForWeatherPostingThenExecuteWeatherMessage() {
        Mockito.when(telegramUserRepository.findAll())
                .thenReturn(List.of(TelegramUser.builder().subscribedOnWeather(true).build()));
        Race race = createNextRace(LocalDateTime.now().plusHours(1));
        Mockito.when(f1SchedulesClient.getNextRace()).thenReturn(race);
        WeatherResponse weatherResponse = createWeatherResponse();
        Mockito.when(weatherClient.getWeatherOnRaceLocation(createLocation())).thenReturn(weatherResponse);

        testObject.scheduleWeatherUpdates();

        Mockito.verify(telegramUserRepository, Mockito.times(1)).findAll();
        Mockito.verify(f1SchedulesClient, Mockito.times(1)).getNextRace();
        Mockito.verify(weatherClient, Mockito.times(1)).getWeatherOnRaceLocation(any());
        Mockito.verify(messageExecutor, Mockito.times(1)).executeMessage(any(), messageCaptor.capture());

        String expectedMessage = "Current weather in  Australia\n\n" + "☀(Clear sky)\n" + "Temperature: 20°C\n"
                + "Feels like: 20°C\n" + "Humidity: 0%\n\n" + "Wind speed: 20 m/s \n" + "Cloudiness: 0%";
        String constructedMessage = messageCaptor.getValue();

        assertEquals(expectedMessage, constructedMessage);
    }

    private static WeatherResponse createWeatherResponse() {
        return WeatherResponse.builder()
                .weather(List.of(Weather.builder().main("clear").description("Clear sky").build()))
                .weatherInfo(WeatherInfo.builder().temp(20).feelTemp(20).humidity(0).build()).windSpeed("20")
                .cloudiness("0").build();
    }
}
