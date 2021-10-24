package com.lomalan.main.rest.model.weather;

import java.io.Serializable;
import lombok.Data;

@Data
public class Weather implements Serializable {
  private String id;
  private String main;
  private String description;
}
