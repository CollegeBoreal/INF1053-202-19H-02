# --- passwords schema

# --- !Ups

CREATE TABLE IF NOT EXISTS `PASSWORDS` (
  `key` VARCHAR(45) NOT NULL,
  `active` DATETIME NOT NULL,
  `secret` VARCHAR(45) NOT NULL,
  `hasher` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`key`, `active`)
);

# --- !Downs

DROP TABLE `PASSWORDS`;
