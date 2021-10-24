package com.lomalan.main;

import com.vdurmont.emoji.EmojiParser;

public enum Emojis {

  //COUNTRIES
  BELGIUM(EmojiParser.parseToUnicode(":be:")),
  NETHERLANDS(EmojiParser.parseToUnicode(":nl:")),
  ITALY(EmojiParser.parseToUnicode(":it:")),
  TURKEY(EmojiParser.parseToUnicode(":tr:")),
  UKRAINE(EmojiParser.parseToUnicode(":ua:")),
  RUSSIA(EmojiParser.parseToUnicode(":poop:")),
  JAPAN(EmojiParser.parseToUnicode(":jp:")),
  MEXICO(EmojiParser.parseToUnicode(":mx:")),
  BRAZIL(EmojiParser.parseToUnicode(":br:")),
  GB(EmojiParser.parseToUnicode(":gb:")),

  //WEATHER
  CLEAR(EmojiParser.parseToUnicode(":sunny:")),
  CLOUDS(EmojiParser.parseToUnicode(":partly_sunny:")),
  RAIN(EmojiParser.parseToUnicode(":cloud_rain:")),
  SNOW(EmojiParser.parseToUnicode(":cloud_snow:")),
  DRIZZLE(EmojiParser.parseToUnicode(":white_sun_behind_cloud_rain:")),

  F1(EmojiParser.parseToUnicode(":f1:"));


  private final String unicodeString;

  Emojis(String unicodeString) {
    this.unicodeString = unicodeString;
  }

  public String getUnicodeString() {
    return unicodeString;
  }

}
