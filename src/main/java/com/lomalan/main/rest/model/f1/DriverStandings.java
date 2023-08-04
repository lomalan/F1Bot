package com.lomalan.main.rest.model.f1;

import lombok.Data;

@Data
public class DriverStandings {
    private String position;
    private String points;
    private String wins;
    private Driver driver;
}
