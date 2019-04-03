# --- passwords schema

# --- !Ups

CREATE TABLE IF NOT EXISTS `PASSWORDS` (
  `key` VARCHAR(45) NOT NULL,
  `active` DATETIME NOT NULL,
  `secret` VARCHAR(255) NOT NULL,
  `hasher` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(45) NULL,
  PRIMARY KEY (`key`, `active`)
);

# --- !Downs

DROP TABLE `PASSWORDS`;
