package com.lomalan.main.rest.client;

import com.lomalan.main.configuration.BotConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TelegramClient {

    private final RestTemplate restTemplate;
    private final BotConfig config;

    @Value("${telegram.api}")
    private String telegramApiUrl;

    private static final String WEB_HOOK_ENDPOINT = "/setWebHook";

    public TelegramClient(RestTemplate restTemplate, BotConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public HttpEntity<String> setWebHook() {
        String urlToExecute = telegramApiUrl + "bot" + config.getToken() + WEB_HOOK_ENDPOINT;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlToExecute).queryParam("url",
                config.getWebHookUrl());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
    }
}
