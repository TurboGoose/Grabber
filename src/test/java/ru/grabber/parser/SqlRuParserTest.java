package ru.grabber.parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.grabber.storage.Post;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SqlRuParserTest {
    static SqlRuParser parser = new SqlRuParser();
    static Properties vacancyProperties = new Properties();

    @BeforeAll
    public static void setup() throws IOException {
        loadVacancyProperties();
    }

    static void loadVacancyProperties() throws IOException {
        try (InputStream is = ClassLoader.getSystemResourceAsStream("vacancy.properties")) {
            vacancyProperties.load(is);
        }
    }

    @Test
    public void parseSingleVacancy() {
        Post actual = parser.detail(vacancyProperties.getProperty("link"));

        Post expected = new Post();
        expected.setId(Integer.parseInt(vacancyProperties.getProperty("id")));
        expected.setName(vacancyProperties.getProperty("name"));
        expected.setText(vacancyProperties.getProperty("text"));
        expected.setLink(vacancyProperties.getProperty("link"));
        expected.setCreated(LocalDateTime.parse(vacancyProperties.getProperty("created")));

        assertThat(actual, is(expected));
    }
}
