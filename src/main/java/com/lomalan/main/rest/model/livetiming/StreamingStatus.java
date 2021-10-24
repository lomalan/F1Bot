package com.lomalan.main.rest.model.livetiming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StreamingStatus {
  @JsonProperty("Status")
  private String status;
}
