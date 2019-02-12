-- -----------------------------------------------------
CREATE TABLE BANDS (
  `band` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`band`, `name`));