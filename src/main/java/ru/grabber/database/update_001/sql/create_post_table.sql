CREATE TABLE IF NOT EXISTS post (
    id INT PRIMARY KEY,
    name VARCHAR(256),
    text TEXT,
    link VARCHAR(512) UNIQUE,
    created DATE
)
