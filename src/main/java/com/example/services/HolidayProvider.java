package com.example.services;

import com.example.model.Holiday;

import java.util.List;

public interface HolidayProvider {
    List<Holiday> getBankHolidays(String country, int year);
}
