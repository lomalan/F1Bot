package com.lomalan.main.rest.client.livetiming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LiveTimingClientTest {

    private final RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
    private final String liveTimingApiMock = "http://localhost";
    private final LiveTimingClient testObject = new LiveTimingClient(restTemplateMock, liveTimingApiMock);

    @Test
    public void whenLiveTimingReturnsNoContentThenReturnEmptyResult() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Mockito.when(restTemplateMock.exchange(liveTimingApiMock.concat("/data"), HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()), String.class)).thenReturn(response);

        assertTrue(testObject.getRaceLiveTiming().isEmpty());
    }

    @Test
    public void whenLiveTimingReturnsOkThenReturnContent() {
        String expectedResponse = "Success";
        ResponseEntity<String> response = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        Mockito.when(restTemplateMock.exchange(liveTimingApiMock.concat("/data"), HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()), String.class)).thenReturn(response);

        var raceLiveTiming = testObject.getRaceLiveTiming();
        assertTrue(raceLiveTiming.isPresent());
        assertEquals(expectedResponse, raceLiveTiming.get());
    }
}
