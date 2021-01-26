package com.example.api;

import com.example.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Controller
public class VacationsController {

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/vacations")
    public ResponseEntity<Map<String, Integer>> getVacationDays(@RequestBody Map<String, String> dateRange, HttpServletRequest httpServletRequest) {

        LocalDate startDate = LocalDate.parse(dateRange.get(START_DATE), DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(dateRange.get(END_DATE), DateTimeFormatter.ISO_DATE);

        Locale locale = RequestContextUtils.getLocaleResolver(httpServletRequest).resolveLocale(httpServletRequest);

        Map<String, Integer> days = holidayService.getDaysMap(startDate, endDate, locale.getCountry());
        return ResponseEntity.of(Optional.of(days));
    }

    @GetMapping("/vacations/clear-cache")
    public ResponseEntity clearCache() {
        holidayService.clearCache();
        return ResponseEntity.accepted().build();
    }
}
