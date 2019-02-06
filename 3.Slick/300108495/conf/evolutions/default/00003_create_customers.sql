# products schema

# --- !Ups

CREATE TABLE customers (
    customer serial PRIMARY KEY,
    name varchar(127) NOT NULL,
    phone varchar(511) NOT NULL
);

# --- !Downs

DROP TABLE customers;