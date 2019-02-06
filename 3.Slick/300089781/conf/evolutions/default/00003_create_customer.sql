# customer schema

# --- !Ups

CREATE TABLE customer (
    id serial PRIMARY KEY,
    sku varchar(31) UNIQUE NOT NULL,
    name varchar(127) NOT NULL,
    description varchar(511) NOT NULL
);

# --- !Downs

DROP TABLE customer;