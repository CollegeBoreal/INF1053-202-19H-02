# --- Sample data for providers schema

# --- !Ups
INSERT INTO `PROVIDERS` (`name`) VALUES ('credentials');

# --- !Downs
DELETE FROM `PROVIDERS` WHERE name = 'credentials';

