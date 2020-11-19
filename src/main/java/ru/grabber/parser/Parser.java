package ru.grabber.parser;

import ru.grabber.Post;

import java.util.List;

public interface Parser {
    List<Post> list(String link);

    Post detail(String link);
}
