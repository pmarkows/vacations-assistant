package com.example.services;

import com.example.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.List;

@ConditionalOnProperty(name = "onlineMode", havingValue = "true")
@Component
public class WebHolidayProvider implements HolidayProvider {

    @Autowired
    private HolidayApiService holidayApiService;

    @Override
    public List<Holiday> getBankHolidays(String country, int year) {
        return holidayApiService.fetchHolidays(country, year);
    }
}
