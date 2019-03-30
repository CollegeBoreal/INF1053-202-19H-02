# --- Sample data for providers schema

# --- !Ups
INSERT INTO `PROVIDERS` (`name`) VALUES ('Credentials');

# --- !Downs
DELETE FROM `PROVIDERS` WHERE name = 'Credentials';

