package com.lomalan.main.bot.commands;

public enum BotCommands {
  NEXT_RACE("Next Race"),
  CURRENT_WEATHER("Subscribe on weather"),
  UNSUB_WEATHER("Unsubscribe from weather"),
  SUB_LIVE("Subscribe on live timing"),
  UNSUB_LIVE("Unsubscribe from live timing");

  private final String commandName;

  BotCommands(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandName() {
    return commandName;
  }
}
