package com.lomalan.main.rest.model.weather;

import java.io.Serializable;
import lombok.Data;

@Data
public class Coordinates implements Serializable {
    private String lat;
    private String lon;
}
