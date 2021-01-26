package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class HolidayService {

    public static final Logger LOGGER = LoggerFactory.getLogger(HolidayService.class);

    public static final String CALENDAR_DAYS = "calendarDays";
    public static final String WORKING_DAYS = "workingDays";

    @Autowired
    private HolidayProxy holidayProxy;

    public Map<String, Integer> getDaysMap(LocalDate startDate, LocalDate endDate, String region) {
        long tik = System.currentTimeMillis();

        int calendarDays = Period.between(startDate, endDate).getDays() + 1; // ponieważ between nie uwzględnia daty końcowej w wyniku

        // TODO: obsłużyć przypadek gdy pocątek urlpu jest w "starym" roku a koniec w "nowym" lub gdy daty są zamienione (startdate póżniej niż enddate)
        List<LocalDate> publicHolidays = holidayProxy.getHolidays(region, startDate.getYear());
        int numberOfHolidays = getNumberOfHolidaysBetween(startDate, endDate, publicHolidays);

        Map<String, Integer> days = createDaysMap(calendarDays, numberOfHolidays);
        LOGGER.info("XX: Vacations calculated in " + (System.currentTimeMillis() - tik) + " miliseconds");
        return days;
    }

//    public Map<String, Integer> getDaysMap(LocalDate startDate, LocalDate endDate) {
//        Locale serverSpecificLocale = LocaleContextHolder.getLocale();
//        return getDaysMap(startDate, endDate, serverSpecificLocale.getCountry());
//    }

    private Map<String, Integer> createDaysMap(int calendarDays, int numberOfHolidays) {
        Map<String,Integer> days = new HashMap<>();
        days.put(CALENDAR_DAYS, calendarDays);
        days.put(WORKING_DAYS, calendarDays - numberOfHolidays);  // dni kal - soboty i niedziele - swieta
        return days;
    }

    // dni robocze, bez Sobot, Niedziel i Swiat
    private int getNumberOfHolidaysBetween(LocalDate startDate, LocalDate endDate, List<LocalDate> publicHolidays) {
        int counter = 0;
        while (startDate.isBefore(endDate)) {
            if (isHoliday(startDate, publicHolidays)) {
                counter++;
            }
            startDate = startDate.plusDays(1);
        }
        return counter;
    }

    private boolean isHoliday(LocalDate aDate, List<LocalDate> publicHolidays) {
        if (publicHolidays.contains(aDate)) {
            return true;
        } else if (DayOfWeek.SATURDAY == aDate.getDayOfWeek()
                || DayOfWeek.SUNDAY == aDate.getDayOfWeek()) {
            return true;
        }
        return false;
    }

    public void clearCache() {
        holidayProxy.clearCache();
    }
}
