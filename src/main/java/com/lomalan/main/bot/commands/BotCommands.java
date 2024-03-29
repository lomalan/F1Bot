package com.lomalan.main.bot.commands;

public enum BotCommands {
  NEXT_RACE("Next Race"),
  RACE_RESULTS("Race results"),
  DRIVERS_STANDING("Drivers Standing"),
  CURRENT_WEATHER("Subscribe on weather"),
  UNSUB_WEATHER("Unsubscribe from weather"),
  SUB_LIVE("Subscribe on live timing"),
  UNSUB_LIVE("Unsubscribe from live timing"),
  SUB_TWITTER("Subscribe on twitter updates"),
  UNSUB_TWITTER("Unsubscribe from twitter updates"),
  SUGGESTION("Suggestion to improve bot"),
  CANCEL("Cancel");

  private final String commandName;

  BotCommands(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandName() {
    return commandName;
  }
}
