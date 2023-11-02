CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

INSERT INTO cities(name) VALUES('Другой'), ('Москва'), ('Санкт-Петербург'), ('Казань'), ('Екатеринбург'), ('Самара'), ('Ростов');