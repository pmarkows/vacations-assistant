package com.example.services;

import com.example.model.Holiday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FileHolidayProviderTest {

    private static final Path holidayJsonFilePath = Path.of("src/test/resources/PL_2021.json");

    @Autowired
    private HolidayProvider holidayProvider;

    @Test
    void parseHolidaysFromJson() throws Exception {
        List<Holiday> bankHolidays = holidayProvider.getBankHolidays("PL", 2021);
        assertEquals(110, bankHolidays.size());
    }
}