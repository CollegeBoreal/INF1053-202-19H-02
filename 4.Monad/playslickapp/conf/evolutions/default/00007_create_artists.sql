# artists schema

# --- !Ups

CREATE TABLE `ARTISTS` (
  `artist` BIGINT(20) NOT NULL,
  `number` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `active` TINYINT(1) NULL,
  `created` TIMESTAMP NULL,
  `login` BIGINT(20) NOT NULL,
  PRIMARY KEY (`artist`),
  INDEX `fk_ARTISTS_LOGINS1_idx` (`login` ASC) VISIBLE,
  CONSTRAINT `fk_ARTISTS_LOGINS1`
    FOREIGN KEY (`login`)
    REFERENCES `LOGINS` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    );

# --- !Downs

DROP TABLE artists;
