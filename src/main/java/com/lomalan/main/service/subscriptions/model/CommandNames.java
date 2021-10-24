package com.lomalan.main.service.subscriptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandNames {
  private String subCommandName;
  private String unsubCommandName;
}
