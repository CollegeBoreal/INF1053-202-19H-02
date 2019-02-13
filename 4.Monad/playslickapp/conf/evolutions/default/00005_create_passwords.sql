# passwords schema

# --- !Ups

CREATE TABLE IF NOT EXISTS `PASSWORDS` (
  `login` BIGINT(20) NOT NULL,
  `hasher` TEXT NULL,
  `password` TEXT NULL,
  `salt` VARCHAR(255) NULL,
  PRIMARY KEY (`login`),
  CONSTRAINT `LOGINS`
    FOREIGN KEY (`login`)
    REFERENCES `LOGINS` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

# --- !Downs

DROP TABLE passwords;
