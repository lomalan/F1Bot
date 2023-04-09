package com.lomalan.main.service.last;

import com.lomalan.main.model.MessageContainer;
import java.util.Optional;

public interface RaceResultsService {

  Optional<MessageContainer> getRaceResults();

}
