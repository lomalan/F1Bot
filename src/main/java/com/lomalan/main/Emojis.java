package com.lomalan.main;

import com.vdurmont.emoji.EmojiParser;

public enum Emojis {

  //COUNTRIES
  HUNGARY(EmojiParser.parseToUnicode(":hu:")),
  FRANCE(EmojiParser.parseToUnicode(":fr:")),
  AUSTRIA(EmojiParser.parseToUnicode(":at:")),
  CANADA(EmojiParser.parseToUnicode(":ca:")),
  AZERBAIJAN(EmojiParser.parseToUnicode(":az:")),
  BAHRAIN(EmojiParser.parseToUnicode(":bh:")),
  MONACO(EmojiParser.parseToUnicode(":id_flag:")),
  SPAIN(EmojiParser.parseToUnicode(":es:")),
  AUSTRALIA(EmojiParser.parseToUnicode(":au:")),
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
  MIST(EmojiParser.parseToUnicode(":fog:")),

  F1(EmojiParser.parseToUnicode(":f1:"));


  private final String unicodeString;

  Emojis(String unicodeString) {
    this.unicodeString = unicodeString;
  }

  public String getUnicodeString() {
    return unicodeString;
  }

}
