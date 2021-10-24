package com.lomalan.main.bot.commands;

public enum BotCommands {
  NEXT_RACE("Next Race"),
  CURRENT_WEATHER("Subscribe on Weather"),
  UNSUB_WEATHER("Unsubscribe from Weather");

  private final String commandName;

  BotCommands(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandName() {
    return commandName;
  }
}
