package com.lomalan.main.rest.model.livetiming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SessionInfo {
  @JsonProperty("Path")
  private String path;
}
