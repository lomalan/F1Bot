package com.lomalan.main.rest.model.weather;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Serializable {
    private String id;
    private String main;
    private String description;
}
