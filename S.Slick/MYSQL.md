# Slick (ORM)

## G8 Template

```
$ sbt new CollegeBoreal/play-slick-mysql.g8 --name=<ID>
```

## MySQL

Lancer un container MySQL

```
$ docker run --name some-mysql --env MYSQL_ROOT_PASSWORD=test --publish 3306:3306 --detach mysql:latest
```

## MySQL CLI

* Rentrer dans le container

```
$ docker exec --interactive --tty some-mysql bash
```

* rentrer dans le CLI MySQL

```
$ root@c2da6ee122d8:~$ mysql -u root -p
```

* creer la db avec les commandes shell (create database de mysql)

```
mysql> create database playdb;
```

## Framework Slick

https://www.playframework.com/documentation/2.6.x/PlaySlick

| Livre                                   | Lien                                            |
|-----------------------------------------|-------------------------------------------------|
| essential-slick-3.epub                  | [essential-slick-3.epub](https://github.com/underscoreio/books/blob/master/essential-slick/essential-slick-3.epub)
