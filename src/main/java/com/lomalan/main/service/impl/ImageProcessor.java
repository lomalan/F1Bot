package com.lomalan.main.service.impl;

import org.telegram.telegrambots.meta.api.objects.InputFile;

public class ImageProcessor {

  private static final String PHOTO_URL =
      "https://www.formula1.com/content/dam/fom-website/2018-redesign-assets/Racehub%20header%20images%2016x9/";
  private static final String PHOTO_NAME = "%s.jpg";

  private ImageProcessor() {}

  public static InputFile getImage(String countryName) {
    return new InputFile(PHOTO_URL.concat(String.format(PHOTO_NAME, countryName)));
  }
}
