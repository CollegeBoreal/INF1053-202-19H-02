# --- !Ups

INSERT INTO PROFILES ( profile, firstname, lastname)
VALUES  (1, 'Safaa', 'Zaoui') ;

# --- !Downs

DELETE FROM PROFILES
 WHERE profile IN (
    1
);
