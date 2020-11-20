package ru.grabber.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class DateTimeParser {
    private final static Map<String, Integer> MONTHS;

    static {
        Map<String, Integer> months = new HashMap<>();
        months.put("янв", 1);
        months.put("фев", 2);
        months.put("мар", 3);
        months.put("апр", 4);
        months.put("май", 5);
        months.put("июн", 6);
        months.put("июл", 7);
        months.put("авг", 8);
        months.put("сен", 9);
        months.put("окт", 10);
        months.put("ноя", 11);
        months.put("дек", 12);
        MONTHS = months;
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        String[] dateAndTime = dateTime.split(", ");
        String date = dateAndTime[0];
        String time = dateAndTime[1];
        LocalDate localDate =
                switch (date) {
                    case "сегодня" -> LocalDate.now();
                    case "вчера" -> LocalDate.now().minusDays(1);
                    default -> parseDate(date);
                };
        LocalTime localTime = LocalTime.parse(time);
        return LocalDateTime.of(localDate, localTime);
    }

    private static LocalDate parseDate(String date) {
        String[] dayMonthYear = date.split(" ");
        int day = Integer.parseInt(dayMonthYear[0]);
        int month = MONTHS.get(dayMonthYear[1]);
        int year = Integer.parseInt(dayMonthYear[2]) + 2000;
        return LocalDate.of(year, month, day);
    }
}
