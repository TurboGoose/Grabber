package ru.grabber.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DateTimeParser {
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
        final List<String> months = List.of(
                "янв", "фев", "мар", "апр",
                "май", "июн", "июл", "авг",
                "сен", "окт", "ноя", "дек");
        String[] dayMonthYear = date.split(" ");
        int day = Integer.parseInt(dayMonthYear[0]);
        int month = months.indexOf(dayMonthYear[1]) + 1;
        int year = Integer.parseInt(dayMonthYear[2]) + 2000;
        return LocalDate.of(year, month, day);
    }
}
