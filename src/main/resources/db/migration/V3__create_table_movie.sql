CREATE TABLE movie (
    id serial PRIMARY KEY,
    title varchar(255) NOT NULL UNIQUE,
    description text,
    release_date date NOT NULL,
    rating numeric (2,1),
    created_at timestamp,
    updated_at timestamp
);