package ru.grabber.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class DateTimeParser {
    private final static Map<String, Integer> MONTHS;

    static {
        MONTHS = new HashMap<>();
        MONTHS.put("янв", 1);
        MONTHS.put("фев", 2);
        MONTHS.put("мар", 3);
        MONTHS.put("апр", 4);
        MONTHS.put("май", 5);
        MONTHS.put("июн", 6);
        MONTHS.put("июл", 7);
        MONTHS.put("авг", 8);
        MONTHS.put("сен", 9);
        MONTHS.put("окт", 10);
        MONTHS.put("ноя", 11);
        MONTHS.put("дек", 12);
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
