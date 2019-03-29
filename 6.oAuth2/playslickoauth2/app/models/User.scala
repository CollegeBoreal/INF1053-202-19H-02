package models

case class User(user: Option[Int],
                key: String,
                active: Boolean,
                created: java.sql.Timestamp)
