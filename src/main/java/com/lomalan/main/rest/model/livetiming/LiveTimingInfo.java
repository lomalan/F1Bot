package com.lomalan.main.rest.model.livetiming;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiveTimingInfo {
  private String raceName;
  private String lapStatus;
  private List<DriverInfo> driverInfo;
}
