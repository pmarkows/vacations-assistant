package com.example.services;

import com.example.model.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class HolidayProxy {

    public static final Logger LOGGER = LoggerFactory.getLogger(HolidayProxy.class);

    @Value("${holidayApi.dateFormat:yyyy-MM-dd}")
    private String dateFormat;

    @Autowired
    private HolidayProvider holidayProvider;

    // To nam służy jako lokalny cache. Ten cache bedzie dostępny dopóki:
    // 1. Bean o nazwie "holidayProxy" istnieje (a ponieważ to Singleton, bean żyje dopóki aplikacja jest uruchomiona)
    // 2. Użytkownik nie zarząda usunięcia odpowiednim requestem
    private List<Holiday> bankHolidaysList = null;

    public List<LocalDate> getHolidays(String country, Integer year) {
        if (bankHolidaysList == null) {
            LOGGER.info("XX: Reading values from file.");
            bankHolidaysList = holidayProvider.getBankHolidays(country, year);
        } else {
            LOGGER.info("XX: Loading values from cache.");
        }

        // Upgrade 1: przerobić ten kod na Stream i użyć flatMap + Collector.toList()
        List<LocalDate> localDates = new ArrayList<>();
        for (Holiday b :
                bankHolidaysList) { // przerobic to tak, żeby format był brany z application.properties
            LocalDate date = LocalDate.parse(b.getDate(), DateTimeFormatter.ofPattern(dateFormat));
            localDates.add(date);
        }

        return localDates;
    }

    public void clearCache() {
        bankHolidaysList = null;
    }
}
