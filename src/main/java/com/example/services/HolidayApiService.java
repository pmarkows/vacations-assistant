package com.example.services;

import com.example.model.Holiday;
import com.example.model.HolidayApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Year;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayApiService {

    @Value("${holidayapi.key}")
    private String apiKey;

    @Value("${holidayapi.url}")
    private String apiUrl;

    /**
     * curl -G -d country="PL" -d year="2020" -d pretty -d key="fc69f6ad-4fca-4d06-89d7-28da34b23a6d" "https://holidayapi.com/v1/holidays"
     * @param countryCode
     * @param year
     * @return
     */
    public List<Holiday> fetchHolidays(String countryCode, int year) {
        WebClient webClient = WebClient.create(apiUrl);
        HolidayApiResponse holidayApiResponse = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/holidays")
                        .queryParam("key", apiKey)
                        .queryParam("country", countryCode)
                        .queryParam("year", year)
                        .build())
                .retrieve()
                .bodyToMono(HolidayApiResponse.class)
                .block();
        return holidayApiResponse.getHolidays().stream().filter(h -> Boolean.TRUE == h.isBankHoliday()).collect(Collectors.toList());
    }

}
