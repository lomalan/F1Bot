package com.lomalan.main.bot.commands;

public enum BotCommands {
  NEXT_RACE("Next Race");


  private final String commandName;

  BotCommands(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandName() {
    return commandName;
  }
}
