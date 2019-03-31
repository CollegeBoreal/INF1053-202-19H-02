# Slick (ORM)

## G8 Template

```
$ sbt new excellalabs/play-slick-postgres.g8 --name=<ID>
```

## PostgreSQL

```
$ docker run --name some-postgres --env POSTGRES_PASSWORD=test --publish 5432:5432 --detach postgres
```

## PosgreSQL CLI

https://www.a2hosting.ca/kb/developer-corner/postgresql/managing-postgresql-databases-and-users-from-the-command-line

* Rentrer dans le container

```
$ docker exec --interactive --tty some-postgres bash
```

* Changer d'utilisateur de root a postgres

```
root@c2da6ee122d8:/# su - postgres
```

* creer la db avec les commandes shell (createdb de postgres)

```
$ postgres@c2da6ee122d8:~$ createdb -O postgres playdb
```

* rentrer dans le CLI postgres

```
postgres@c2da6ee122d8:~$ psql
```

* dans le PSQL CLI creer les permissions

```
postgres=# GRANT ALL ON DATABASE playdb TO postgres;
```


## Framework Slick

https://www.playframework.com/documentation/2.6.x/PlaySlick

| Livre                                   | Lien                                            |
|-----------------------------------------|-------------------------------------------------|
| essential-slick-3.epub                  | [essential-slick-3.epub](https://github.com/underscoreio/books/blob/master/essential-slick/essential-slick-3.epub)
