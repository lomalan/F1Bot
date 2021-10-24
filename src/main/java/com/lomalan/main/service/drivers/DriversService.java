package com.lomalan.main.service.drivers;

import com.lomalan.main.rest.model.f1.DriverStandings;
import java.util.List;

public interface DriversService {

  List<DriverStandings> getCurrentSeasonStandings();
}
