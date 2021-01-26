package com.example.services;

import com.example.model.Holiday;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ConditionalOnProperty(name = "onlineMode", havingValue = "false")
@Component
public class FileHolidayProvider implements HolidayProvider {

    public static final String HOLIDAY_JSON_FILE_DIRECTORY = "src/main/resources/holidays/";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Holiday> getBankHolidays(String country, int year) {

        StringBuilder holidayJsonFileNameBuilder = new StringBuilder(HOLIDAY_JSON_FILE_DIRECTORY)
                .append(country).append("_").append(year).append(".json");
        List<Holiday> bankHolidays = null;
        try {
            bankHolidays = parseHolidaysFromJson(Path.of(holidayJsonFileNameBuilder.toString()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bankHolidays;
    }

    private List<Holiday> parseHolidaysFromJson(Path path) throws JsonProcessingException {
        String holidaysJson = null;
        try {
            holidaysJson = fetchHolidays(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of(objectMapper.readValue(holidaysJson, Holiday[].class));
    }

    String fetchHolidays(Path path) throws IOException {
        String holidaysJsonAsString = Files.readString(path);
        return holidaysJsonAsString;
    }
}
