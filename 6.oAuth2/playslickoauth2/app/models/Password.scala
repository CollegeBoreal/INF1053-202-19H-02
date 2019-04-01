package models

case class Password(key: String,
                    active: java.sql.Timestamp,
                    password: String,
                    hasher: String,
                    salt: Option[String] = None)
