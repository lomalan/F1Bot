package com.lomalan.main.service.last.impl;

import com.lomalan.main.model.MessageContainer;
import com.lomalan.main.rest.client.f1.F1ResultsClient;
import com.lomalan.main.rest.model.f1.RaceResults;
import com.lomalan.main.service.impl.MessageConstructor;
import com.lomalan.main.service.last.RaceResultsService;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RaceResultsServiceImpl implements RaceResultsService {

    private final F1ResultsClient client;

    public RaceResultsServiceImpl(F1ResultsClient client) {
        this.client = client;
    }

    @Override
    public Optional<MessageContainer> getRaceResults() {
        RaceResults raceResults;
        try {
            raceResults = client.getRaceResults();
            String raceRaceMessage = MessageConstructor.constructRaceResultsMassage(raceResults);
            return Optional.of(getMessageContainer(raceRaceMessage));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    private MessageContainer getMessageContainer(String raceRaceMessage) {
        return constructMessageContainer(raceRaceMessage);
    }

    private MessageContainer constructMessageContainer(String text) {
        return new MessageContainer(text);
    }
}
