package com.lomalan.main;

import com.vdurmont.emoji.EmojiParser;

public enum Emojis {

  BELGIUM(EmojiParser.parseToUnicode(":be:")),
  UKRAINE(EmojiParser.parseToUnicode(":ua:"));

  private final String unicodeString;

  Emojis(String unicodeString) {
    this.unicodeString = unicodeString;
  }

  public String getUnicodeString() {
    return unicodeString;
  }

}
