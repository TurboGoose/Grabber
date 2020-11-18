CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    topic VARCHAR(256),
    author VARCHAR(40),
    replies INT,
    views INT,
    publication_date DATE
)