package ru.grabber.storage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private final Connection connection;

    public PsqlStore(Properties config) {
        try {
            connection = DriverManager.getConnection(config.getProperty("url"), config);
        } catch (Exception exc) {
            throw new IllegalStateException(exc);
        }
    }

    @Override
    public boolean save(Post post) {
        boolean success;
        try (PreparedStatement st = connection.prepareStatement(
                "INSERT INTO post(id, name, text, link, created) VALUES(?, ?, ?, ?, ?);"
        )) {
            st.setInt(1, post.getId());
            st.setString(2, post.getName());
            st.setString(3, post.getText());
            st.setString(4, post.getLink());
            st.setObject(5, post.getCreated());
            success = st.executeUpdate() == 1;
        } catch (SQLException exc) {
            throw new IllegalStateException(exc);
        }
        return success;
    }

    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(
                "SELECT * FROM post;"
        )) {
            ResultSet selected = st.executeQuery();
            while (selected.next()) {
                Post post = new Post();
                post.setId(selected.getInt("id"));
                post.setName(selected.getString("name"));
                post.setText(selected.getString("text"));
                post.setLink(selected.getString("link"));
                post.setCreated(selected.getObject("created", LocalDateTime.class));
                result.add(post);
            }
        } catch (SQLException exc) {
            throw new IllegalStateException(exc);
        }
        return result;
    }

    @Override
    public Post findById(int id) {
        Post result = new Post();
        try (PreparedStatement st = connection.prepareStatement(
                "SELECT * FROM post WHERE id=?;"
        )) {
            st.setInt(1, id);
            ResultSet selected = st.executeQuery();
            if (selected.next()) {
                result.setId(selected.getInt("id"));
                result.setName(selected.getString("name"));
                result.setText(selected.getString("text"));
                result.setLink(selected.getString("link"));
                result.setCreated(selected.getObject("created", LocalDateTime.class));
            }
        } catch (SQLException exc) {
            throw new IllegalStateException(exc);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
