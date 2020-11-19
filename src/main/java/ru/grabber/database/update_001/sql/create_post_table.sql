CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    name VARCHAR(256),
    text TEXT,
    link VARCHAR(512) UNIQUE,
    created DATE
)
