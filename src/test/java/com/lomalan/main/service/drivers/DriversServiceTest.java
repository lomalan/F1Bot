package com.lomalan.main.service.drivers;


import com.lomalan.main.bot.commands.BotCommands;
import com.lomalan.main.dao.model.TelegramUser;
import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.rest.client.f1.F1StandingsClient;
import com.lomalan.main.rest.model.f1.Driver;
import com.lomalan.main.rest.model.f1.DriverStandings;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.lomalan.main.TestResources.createUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DriversServiceTest {

    private final F1StandingsClient f1StandingsClient = mock(F1StandingsClient.class);
    private final DriversService driversService = new DriversService(f1StandingsClient);

    @Test
    void whenCommandIsNotDriversStandingThenReturnEmptyResult() {
        Update update = createUpdate("Some command");

        Optional<MessageContainer> result = driversService.processMessage(update, TelegramUser.builder().build());
        assertTrue(result.isEmpty());
    }

    @Test
    void whenCommandIsDriversStandingButErrorInClientThenReturnEmptyContainer() throws IOException {
        Update update = createUpdate(BotCommands.DRIVERS_STANDING.getCommandName());

        when(f1StandingsClient.getDriverStandings())
                .thenThrow(new IOException("Resource not found"));

        Optional<MessageContainer> result = driversService.processMessage(update, TelegramUser.builder().build());
        assertTrue(result.isPresent());
        String expectedText = "Sorry, can't return driver standings. Please try again later.";
        assertEquals(expectedText, result.get().getText());
    }

    @Test
    void whenCommandIsDriversStandingThenReturnDriverStandings() throws IOException {
        Update update = createUpdate(BotCommands.DRIVERS_STANDING.getCommandName());

        when(f1StandingsClient.getDriverStandings())
                .thenReturn(Collections.singletonList(createStandings()));
        Optional<MessageContainer> result = driversService.processMessage(update, TelegramUser.builder().build());
        assertTrue(result.isPresent());
        String expectedText = "Current drivers standing \n\n"
                + "POS NAME POINTS \n\n"
                + "1. ALO 100";
        assertEquals(expectedText, result.get().getText());
    }

    private DriverStandings createStandings() {
        DriverStandings driverStandings = new DriverStandings();
        driverStandings.setPosition("1");
        driverStandings.setWins("3");
        driverStandings.setPoints("100");
        Driver driver = new Driver();
        driver.setCode("ALO");
        driverStandings.setDriver(driver);
        return driverStandings;
    }
}
