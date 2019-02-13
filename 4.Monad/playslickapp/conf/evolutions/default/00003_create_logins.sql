# logins schema

# --- !Ups
CREATE TABLE IF NOT EXISTS `LOGINS` (
  `login` BIGINT(20) NOT NULL,
  `providerId` VARCHAR(45) NULL,
  `providerKey` VARCHAR(45) NULL,
  PRIMARY KEY (`login`)
  );
# --- !Downs

DROP TABLE logins;
