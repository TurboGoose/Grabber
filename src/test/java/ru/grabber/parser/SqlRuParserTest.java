package ru.grabber.parser;

import org.junit.jupiter.api.Test;
import ru.grabber.storage.Post;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SqlRuParserTest {
    SqlRuParser parser = new SqlRuParser();
    Properties vacancyProperties = new Properties();

    @Test
    public void parseSingleVacancy() throws IOException {
        setup();
        Post actual = parser.detail(vacancyProperties.getProperty("link"));

        Post expected = new Post();
        expected.setId(Integer.parseInt(vacancyProperties.getProperty("id")));
        expected.setName(vacancyProperties.getProperty("name"));
        expected.setText(vacancyProperties.getProperty("text"));
        expected.setLink(vacancyProperties.getProperty("link"));

        expected.setCreated(LocalDateTime.parse(vacancyProperties.getProperty("created")));

        assertThat(actual, is(expected));
    }

    void setup() throws IOException {
        loadVacancyProperties();
        convertToUtf8();
    }

    void loadVacancyProperties() throws IOException {
        try (InputStream is = ClassLoader.getSystemResourceAsStream("vacancy.properties")) {
            vacancyProperties.load(is);
        }
    }

    void convertToUtf8() {
        for (String key : vacancyProperties.stringPropertyNames()) {
            String value = vacancyProperties.getProperty(key);
            vacancyProperties.setProperty(key,
                    new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        }
    }
}