-- -----------------------------------------------------
CREATE TABLE PROFILE (
  `firstname` BIGINT(20) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,
  PRIMARY KEY (`firstname`, `lastname`));