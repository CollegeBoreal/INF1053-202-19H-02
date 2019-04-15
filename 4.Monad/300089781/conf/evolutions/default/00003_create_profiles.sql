# products schema

# --- !Ups

CREATE TABLE PROFILES (
  `profile` INT NOT NULL AUTO_INCREMENT COMMENT '	',
  `name` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,

  PRIMARY KEY (`profile`)
);

# --- !Downs

DROP TABLE PROFILES;
