# Slick (ORM)

## G8 Template

```
$ sbt new CollegeBoreal/play-slick-mysql.g8 --name=<ID>
```

## Lancer un container (MySQL ou Postgres)

* [MySQL](MYSQL.md)

* [Postgres](POSTGRES.md)


## Lancer le REPL

```
scala> val db = Database.forConfig("slick.dbs.default.db")
db: slick.jdbc.MySQLProfile.backend.Database = slick.jdbc.JdbcBackend$DatabaseDef@2e9a2acc
```

| Livre                                   | Lien                                            |
|-----------------------------------------|-------------------------------------------------|
| essential-slick-3.epub                  | [essential-slick-3.epub](https://github.com/underscoreio/books/blob/master/essential-slick/essential-slick-3.epub)

## Framework Slick

https://www.playframework.com/documentation/2.6.x/PlaySlick
