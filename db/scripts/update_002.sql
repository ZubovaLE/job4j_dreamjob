CREATE TABLE candidates (
    id SERIAL PRIMARY KEY,
    lastName TEXT NOT NULL,
    firstName TEXT NOT NULL,
    middleName TEXT,
    created TIMESTAMPTZ,
    email TEXT,
    phoneNumber TEXT,
    age SMALLINT,
    photo TEXT,
    gender TEXT,
    city_id INTEGER REFERENCES cities(id)
);