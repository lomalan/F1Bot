package com.lomalan.main.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static java.lang.String.format;

@Slf4j
public class ImageProcessor {

  private static final String PHOTO_URL =
      "https://www.formula1.com/content/dam/fom-website/2018-redesign-assets/Racehub%20header%20images%2016x9/";
  private static final String PHOTO_NAME = "%s.jpg";

  private ImageProcessor() {}

  public static Optional<InputFile> getImage(String countryName) {
    String photoUrl = PHOTO_URL.concat(format(PHOTO_NAME, countryName.replaceAll(StringUtils.SPACE, "_")));
    if (!isImageValid(photoUrl)) {
      return Optional.empty();
    }
    return Optional.of(new InputFile(photoUrl));
  }

  private static boolean isImageValid(String photoUrl) {
    try {
      URL url = new URL(photoUrl);
      ImageIO.read(url);
      return true;
    } catch (MalformedURLException e) {
      log.error(e.getMessage(), e);
    } catch (IOException e) {
      log.info("Image was not found. Message: " + e.getMessage());
    }
    return false;
  }
}
