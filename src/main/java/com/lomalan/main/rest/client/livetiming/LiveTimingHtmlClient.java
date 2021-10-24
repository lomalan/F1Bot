package com.lomalan.main.rest.client.livetiming;

import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParserListener;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import com.lomalan.main.rest.model.livetiming.DriverInfo;
import com.lomalan.main.rest.model.livetiming.LiveTimingInfo;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LiveTimingHtmlClient {

  @Value("${live.f1planet.url}")
  private String liveTimingUrl;
  private static final String SCHUMACHER_SHORT_NAME = "SCH";
  private static final String SCHUMACHER_SHORT_NAME_REPLACEMENT = "MSC";
  private static final String RACE_NAME_SECTION_NAME = "contestgroupnameappend";
  private static final String RACE_STATUS_SECTION_NAME = "//span[@class='mobile mobile_date_view']";
  private static final String DRIVERS_INFO_SECTION_NAME = "driversTable";

  public Optional<LiveTimingInfo> getLiveTimingInfo() {
    try (WebClient webClient = new WebClient()) {
      HtmlPage page = webClient.getPage(liveTimingUrl);
      setupWebClient(webClient);
      Pair<String, String> raceNameAndStatus = getRaceNameAndStatus(page);
      List<DriverInfo> driverInfoTwos = getDriversInfo(page);
      return Optional
          .of(new LiveTimingInfo(raceNameAndStatus.getFirst(), raceNameAndStatus.getSecond(), driverInfoTwos));
    } catch (IOException exc) {
      log.error(exc.getMessage());
    }

    return Optional.empty();
  }

  private List<DriverInfo> getDriversInfo(HtmlPage page) {
    HtmlTable driversTable = page.getHtmlElementById(DRIVERS_INFO_SECTION_NAME);

    return driversTable.getRows()
        .stream().map(this::mapToDriverInfo)
        .collect(Collectors.toList());
  }

  private void setupWebClient(WebClient webClient) {
    webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
    webClient.setIncorrectnessListener((s, o) -> {
      //empty
    });
    webClient.setHTMLParserListener(new HTMLParserListener() {
      @Override
      public void error(String s, URL url, String s1, int i, int i1, String s2) {
        //empty
      }

      @Override
      public void warning(String s, URL url, String s1, int i, int i1, String s2) {
        //empty
      }
    });

    webClient.setCssErrorHandler(new SilentCssErrorHandler());
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setCssEnabled(false);
    webClient.waitForBackgroundJavaScriptStartingBefore(10000);
  }

  private Pair<String, String> getRaceNameAndStatus(HtmlPage page) {
    String raceName = page.getHtmlElementById(RACE_NAME_SECTION_NAME).asNormalizedText();
    List<Object> byXPath = page.getByXPath(RACE_STATUS_SECTION_NAME);
    if (byXPath.isEmpty()) {
      return Pair.of(raceName, StringUtils.EMPTY);
    }
    HtmlSpan htmlSpan = (HtmlSpan) byXPath.get(0);
    String raceStatus = htmlSpan.getChildElements().iterator().next().asNormalizedText();
    return Pair.of(raceName, raceStatus);
  }

  private DriverInfo mapToDriverInfo(HtmlTableRow row) {
    return new DriverInfo(row.getCells().get(0).asNormalizedText(),
        getDriverName(row.getCells().get(1).asNormalizedText()),
        row.getCells().get(3).asNormalizedText(),
        row.getCells().get(4).asNormalizedText());
  }

  private  String getDriverName(String stringValue) {
    if (stringValue.contains(StringUtils.SPACE)) {
      return getDriverShortName(stringValue);
    }
    return stringValue;
  }

  private String getDriverShortName(String stringValue) {
    String shortName = stringValue.split(StringUtils.SPACE)[1].substring(0, 3).toUpperCase(Locale.ROOT);
    if (shortName.equals(SCHUMACHER_SHORT_NAME)) {
      return SCHUMACHER_SHORT_NAME_REPLACEMENT;
    }
    return shortName;
  }

}
