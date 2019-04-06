# Slick (ORM)

## Prerequis: Lancer un container (MySQL ou Postgres)

* [MySQL](MYSQL.md)

* [Postgres](POSTGRES.md)


## Creer un nouveau projet [G8 Template]

```
$ sbt new CollegeBoreal/play-slick-mysql.g8 --name=<ID>
```

## Dans le projet, lancer le REPL (sbt console)

```
scala> import slick.jdbc.MySQLProfile.api._
import slick.jdbc.MySQLProfile.api._
```

http://slick.lightbend.com/doc/3.3.0/database.html#using-typesafe-config

```
scala> val db = Database.forConfig("slick.dbs.default.db")
db: slick.jdbc.MySQLProfile.backend.Database = slick.jdbc.JdbcBackend$DatabaseDef@2e9a2acc
```

## Suivre la documentation du livre

| Livre                                   | Lien                                            |
|-----------------------------------------|-------------------------------------------------|
| essential-slick                         | https://underscore.io/books/essential-slick/    |

## Framework Slick

http://slick.lightbend.com/

https://www.playframework.com/documentation/2.6.x/PlaySlick
