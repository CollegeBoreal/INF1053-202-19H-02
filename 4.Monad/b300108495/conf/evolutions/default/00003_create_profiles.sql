
-- -----------------------------------------------------
# --- !Ups
CREATE TABLE PROFILES (
  `profile` BIGINT (20) NOT NULL,
  `firstname` VARCHAR (45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,
  PRIMARY KEY (`profile`, `lastname`));

# --- !Downs
DROP TABLE PROFILES ;