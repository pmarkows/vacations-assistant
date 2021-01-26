package com.example.services;

import com.example.model.Holiday;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
//@SpringBootTest
//class HolidayApiServiceTest {
//
//    @Autowired
//    private HolidayApiService holidayApiService;
//
//    @Test
//    void fetchHolidays() {
//        List<Holiday> holidays = holidayApiService.fetchHolidays("PL", Year.of(2021));
//        assertEquals(13, holidays.size());
//    }
//}