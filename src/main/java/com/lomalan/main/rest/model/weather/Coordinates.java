package com.lomalan.main.rest.model.weather;

import lombok.Data;

import java.io.Serializable;

@Data
public class Coordinates implements Serializable {
  private String lat;
  private String lon;
}
