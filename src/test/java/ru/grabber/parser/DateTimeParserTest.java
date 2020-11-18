package ru.grabber.parser;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DateTimeParserTest {

    @Test
    public void parseToday() {
        String dateTime = "сегодня, 11:35";
        LocalDateTime actual = DateTimeParser.parseDateTime(dateTime);
        LocalDateTime expected = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.parse(dateTime.split(", ")[1])
        );
        assertThat(actual, is(expected));
    }

    @Test
    public void parseYesterday() {
        String dateTime = "вчера, 22:09";
        LocalDateTime actual = DateTimeParser.parseDateTime(dateTime);
        LocalDateTime expected = LocalDateTime.of(
                LocalDate.now().minusDays(1),
                LocalTime.parse(dateTime.split(", ")[1])
        );
        assertThat(actual, is(expected));
    }

    @Test
    public void parseCertainDate() {
        String dateTime = "16 ноя 20, 14:44";
        LocalDateTime actual = DateTimeParser.parseDateTime(dateTime);
        LocalDateTime expected = LocalDateTime.of(
                LocalDate.of(2020, 11, 16),
                LocalTime.parse(dateTime.split(", ")[1])
        );
        assertThat(actual, is(expected));
    }
}
