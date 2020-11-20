package ru.grabber.storage;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

// version without rollbacks
class PsqlStoreTest {
    static PsqlStore store;
    static Post testPost;

    @BeforeAll
    static void setup() throws IOException {
        loadStore();
        loadTestPost();
    }

    static void loadStore() throws IOException {
        Properties storeConfig = new Properties();
        try (InputStream is = ClassLoader.getSystemResourceAsStream("store.properties")) {
            storeConfig.load(is);
        }
        store = new PsqlStore(storeConfig);
    }

    static void loadTestPost() throws IOException {
        Properties vacancyProperties = new Properties();
        try (InputStream is = ClassLoader.getSystemResourceAsStream("vacancy.properties")) {
            vacancyProperties.load(is);
        }
        testPost = new Post();
        testPost.setId(Integer.parseInt(vacancyProperties.getProperty("id")));
        testPost.setName(vacancyProperties.getProperty("name"));
        testPost.setText(vacancyProperties.getProperty("text"));
        testPost.setLink(vacancyProperties.getProperty("link"));
        testPost.setCreated(LocalDateTime.parse(vacancyProperties.getProperty("created")));
    }

    @AfterAll
    static void teardown() throws Exception {
        store.close();
    }

    @Test
    @Order(1)
    public void savePost() {
        assertThat(store.save(testPost), is(true));
    }

    @Test
    @Order(2)
    public void findPostById() {
        assertThat(store.findById(testPost.getId()), is(testPost));
    }

    @Test
    @Order(3)
    public void getAllWhenNotEmpty() {
        assertThat(store.getAll(), is(List.of(testPost)));
    }
}
