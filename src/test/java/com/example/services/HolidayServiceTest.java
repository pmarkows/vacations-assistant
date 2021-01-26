package com.example.services;

import com.example.model.Holiday;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HolidayServiceTest {

    @Autowired
    private HolidayService holidayService;

    @Test
    void fetchHolidays() {
        Map<String, Integer> vacationDays = holidayService
                .getDaysMap(LocalDate.of(2021, 1,1),
                        LocalDate.of(2021,1,31),
                        "PL");
        assertEquals(31, vacationDays.get(HolidayService.CALENDAR_DAYS));
        assertEquals(22, vacationDays.get(HolidayService.WORKING_DAYS));
    }
}